# The Most Useful App
Madcamp 1st week assignment

## Contributors
- 김사은 (숙명여자대학교) 9764kim@naver.com
- 황인준 (한국과학기술원) yuwol@kaist.ac.kr

## Description
본 프로젝트는 탭 세 개를 가지는 앱 제작을 목표로 합니다.
구현한 탭은 아래와 같습니다:

1. Contacts
2. Gallery
3. Todo List

### Contacts
<div style="display: flex;">
<img src="/images/contact_permission.jpg" alt="Contact permission screenshot" width="240"/>
<img src="/images/phone_permision.jpg" alt="Phone permission screenshot" width="240"/>
<img src="/images/contact_tab.jpg" alt="Contact tab screenshot" width="240"/>
</div>
연락처 탭은 본 앱의 기본 탭입니다.
앱을 열거나 다른 탭에서 돌아올 때마다 연락처 및 전화 권한이 있는지 검사하고, 없을 시 권한을 요청합니다.
연락처 권한을 허용하면 사용자의 연락처 앱에 저장된 데이터를 불러와 화면에 표시합니다.
불러오는 데이터는 아래와 같습니다:

1. 이름
2. 전화번호
3. 프로필 이미지

전화 권한을 허용했을 경우 화면에 표시된 연락처를 탭하면 해당하는 전화번호로 전화를 겁니다.

### Gallery

갤러리 탭이 열리면 기기 사진, 미디어, 파일 접근 권한을 검사하고, 없을 시 권한을 요청합니다.
권한을 허용하면 사용자의 갤러리 앱에 저장된 이미지 데이터를 불러와 화면에 표시합니다.

### Todo List
<div style="display: flex;">
<img src="/images/todo_tab.jpg" alt="Todo tab screenshot" width="240"/>
<img src="/images/create_todo_dialog.jpg" alt="Create todo dialog screenshot" width="240"/>
<img src="/images/delete_todo.jpg" alt="Delete todo screenshot" width="240"/>
<img src="/images/delete_all_todos_dialog.jpg" alt="Delete all todos dialog screenshot" width="240"/>
</div>
간단한 텍스트를 저장하고 화면에 리스트 형태로 나타내는 탭입니다.
SQLite를 이용하여 CRUD를 지원합니다:

#### Create
우측 하단의 '+' 버튼을 클릭하면 텍스트를 입력할 수 있는 모달이 표시됩니다.
텍스트를 입력하고 제출 버튼을 누르면 텍스트가 저장됩니다.

#### Retrieve
앱을 종료하거나 다른 탭으로 전환한 후 본 탭으로 다시 돌아오면 DB에서 아이템 목록을 불러와 화면에 나타냅니다.

#### Update
각 아이템을 탭하면 완료 여부를 변경할 수 있습니다.

#### Delete
각 아이템을 양옆으로 밀어서 삭제할 수 있습니다.
우측 상단의 휴지통 아이콘을 클릭하여 모든 아이템을 삭제할 수 있습니다.
