###1. Decorate commit messages
```yml
✨ :sparkles: feat : 새로운 기능
🐛 :bug: fix : 버그
♻️ :recycle: refactor: 코드 리팩토링
💅 :nail_care: style: 코드 의미에 영향을 주지 않는 변경사항 (형식 지정, 세미콜론 누락 등)
📝 :memo: docs : 문서의 추가, 수정, 삭제
🧪 :test_tube: test : 테스트 추가, 수정, 삭제 (비즈니스 로직에 변경 없음)
📦️ :package: chore: 기타 변경사항 (빌드 부분 혹은 패키지 매니저 수정사항)
```


### 2. Set google properties in your application.properties

```yml
#[google]
google.client.key=xxxxxxxx
#https://cloud.google.com/api에서 발급받을 수 있습니다. 

#[dasta]
data.client.key=xxxxx

#https://www.data.go.kr/ 에서 발급받을 수 있습니다. 


#[openai]
openai.api.key=xxxxx
openai.model=gpt-3.5-turbo
openai.api.url=https://api.openai.com/v1/chat/completions

#https://platform.openai.com/account/api-keys에서 발급받을 수 있습니다. 
```
