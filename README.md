# 너의 재능, 샘이나!
2023-2학기 사용자 인터페이스 실습 너의 재능, 샘이나! 팀의 레포지토리입니다.  
![1](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/c354b147-91d5-4087-a393-06fbe30b4c78)  

![2](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/f753f236-1bc6-47a0-baef-f3f17bdd1653)  

![4](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/1b0662ae-4e04-40c5-aceb-559c7af25d46)  

![5](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/a1bd7174-c263-49b3-b0fd-9de3ecee2517)  

![6](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/3bfcbc3c-84e7-4c4a-9437-faf9e0d82786)  

![7](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/7e0a3445-ebe7-4bba-a548-0f60985b01b1)  

![8](https://github.com/Sem-in-a/Semi-na-function/assets/85864699/95f41b5b-d326-4dc6-ab4c-c72161b12a14)    

## semina team's convention
- ### XML id convention

- `XML파일명_피그마에서 선언한 이름` 으로 짓는다
- 변수명이 중복되는 경우 숫자로 구분한다

### Activity Convention/ Fragment Convention

- 따로 제한은 없으나 최대한 피그마 이름을 따라간다.

### Adapter Convention

- RecyclerView Adapter나 FragmentState Adapter는 재사용성이 크기 때문에 범용적인 이름으로 이름을 짓는다.

## git commit convention

| 커밋 헤더 | 헤더 설명 |
| --- | --- |
| feat | 기능추가 |
| design | CSS 등 사용자 UI 디자인 변경 |
| style | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우 |
| comment | 필요한 주석 추가, 변경 및 삭제 |
| fix | 버그 수정 |
| refactor | 프로덕션 코드 리팩토링, 새로운 기능이나 버그 수정없이 현재 구현을 개선한 경우 |
| docs | README.md 수정 |
| rename | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| remove | 파일을 삭제하는 작업만 수행한 경우 |
| test | 테스트 코드, 리펙토링 테스트 코드 추가, Production Code(실제로 사용하는 코드) 변경 없음 |
| chore | 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음 |
| !BREAKING | CHANGE 커다란 API 변경의 경우 |
| !HOTFIX | 급하게 치명적인 버그를 고쳐야하는 경우 |

## git branch naming convention
`git branch <{category}/{issue-number}-{branch-description}>`

**category**
- 커밋 헤더 이름
- feature의 경우 feat/
- fix의 경우 fix/

**description**
- 브랜치의 목적을 설명 (kebab-case로 작성)

**Example**
- `git branch feat/1-create-login-page-layout`
