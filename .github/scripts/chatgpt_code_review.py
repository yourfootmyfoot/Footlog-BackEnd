import openai
import os
import requests
import json

# OpenAI API 키 설정
openai.api_key = os.getenv("OPENAI_API_KEY")

def get_modified_files():
    # GitHub Actions 환경변수에서 PR의 변경된 파일 목록을 가져오는 함수
    repo = os.getenv("GITHUB_REPOSITORY")
    pr_number = os.getenv("GITHUB_REF").split('/')[-1]
    token = os.getenv("GITHUB_TOKEN")
    
    url = f"https://api.github.com/repos/{repo}/pulls/{pr_number}/files"
    headers = {"Authorization": f"token {token}"}

    response = requests.get(url, headers=headers)
    
    # 응답 내용 출력
    print(f"GitHub API Response: {response.text}")
    
    # 응답을 JSON 형식으로 변환 시도
    try:
        files = response.json()
    except json.JSONDecodeError:
        print("Error: Unable to decode JSON response")
        return []

    modified_files = []
    
    # 응답이 리스트인 경우 처리
    if isinstance(files, list):
        for file in files:
            print(f"Processing file entry: {file}")  # 파일 정보를 로그로 출력
            if file.get("status") == "modified":
                modified_files.append(file.get("filename"))
    else:
        print(f"Unexpected response format: {files}")
    
    return modified_files

def review_code(file_path):
    with open(file_path, "r") as f:
        code = f.read()

    # ChatGPT API로 코드 리뷰 요청
    response = openai.ChatCompletion.create(
      model="gpt-4",
      messages=[
        {"role": "system", "content": "You are a senior software engineer tasked with reviewing code."},
        {"role": "user", "content": f"Please review the following code:\n\n{code}"}
      ]
    )

    return response["choices"][0]["message"]["content"]

def main():
    modified_files = get_modified_files()

    review_results = {}

    for file in modified_files:
        review = review_code(file)
        review_results[file] = review
        print(f"Review for {file}:\n{review}\n")

    # 코드 리뷰 결과를 GitHub에 주석으로 달기
    pr_number = os.getenv("GITHUB_REF").split('/')[-1]
    repo = os.getenv("GITHUB_REPOSITORY")
    token = os.getenv("GITHUB_TOKEN")
    
    comment_body = "### Automated Code Review by ChatGPT\n\n"
    for file, review in review_results.items():
        comment_body += f"**File**: {file}\n```\n{review}\n```\n\n"

    url = f"https://api.github.com/repos/{repo}/issues/{pr_number}/comments"
    headers = {"Authorization": f"token {token}"}
    data = {"body": comment_body}
    requests.post(url, headers=headers, data=json.dumps(data))

if __name__ == "__main__":
    main()
