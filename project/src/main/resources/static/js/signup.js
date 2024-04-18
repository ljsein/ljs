$(function() {
	$("#showSignUp").click(function() {
		// 현재 페이지에서 새로운 윈도우 창을 열어서 회원가입 폼을 보여줌
		var signUpWindow = window.open("/client/writeForm", "_blank");

		// 윈도우 창에 삽입할 HTML 코드
		var signUpContent = `
            <html>
            <head>
                <title>회원가입 폼</title>
                    <script>
                    $("#submitSignUp").click(function () {
                        var username = $("#username").val();
                        var password = $("#password").val();
                        // 여기에 회원가입 처리 로직을 추가할 수 있습니다.
                        alert("회원가입 완료!");
                        // 윈도우 창 닫기
                        window.close();
                    });
                </script>
            </head>
      
 <style>
 *{
    box-sizing: border-box; /*전체에 박스사이징*/
    outline: none; /*focus 했을때 테두리 나오게 */
}

body{
    font-family: 'Noto Sans KR', sans-serif;
    font-size:14px;
    background-image: url('/image/logo_background.png');
    background-position: center;
}

a{
    text-decoration: none;
    color: #222;
}

/*member sign in*/
.member{
    width: 400px;
    /* border: 1px solid #000; */
    margin: auto; /*중앙 정렬*/
    padding: 0 20px;
    margin-bottom: 20px;
}

.member .logo{
    /*로고는 이미지라 인라인 블록이니까 마진 오토 안됨 블록요소만 됨 */
    display: block;
    margin :50px auto;
}

.member .field{
    margin :5px 0; /*상하로 좀 띄워주기*/
}

.member b{
    /* border: 1px solid #000; */
    display: block; /*수직 정렬하기 */
    margin-bottom: 5px;
}

/*input 중 radio 는 width 가 100%면 안되니까 */
.member input:not(input[type=radio]),.member select{
    border: 1px solid #dadada;
    padding: 15px;
    width: 100%;
    margin-bottom: 5px;
}
.member input[type=button] {
    background-image: url('/image/background.jpg');
   color: white;
    -webkit-mask-image: linear-gradient(45deg,#000 25%,rgba(0,0,0,.2) 50%,#000 75%);
    mask-image: linear-gradient(45deg,#000 25%,rgba(0,0,0,.2) 50%,#000 75%);
    -webkit-mask-size: 800%;
    mask-size: 800%;
    -webkit-mask-position: 0;
    mask-position: 0;
}

.member input[type=button]:hover {
    transition: mask-position 2s ease,-webkit-mask-position 2s ease;
    -webkit-mask-position: 120%;
    mask-position: 120%;
    opacity: 1;
}
.field.birth div{ /*field 이면서 birth*/
    display: flex;
    gap:10px; /*간격 벌려줄때 공식처럼 사용핟나 */
}

/* .field.birth div > * {  gap 사용한거랑 같은 효과를 줌 
    flex:1;
} */

.field.tel-number div {
    display: flex;
}

.field.tel-number div input:nth-child(1){
    flex:2;
}

.field.tel-number div input:nth-child(2){
    flex:1;
}

.field.gender div{
    border: 1px solid #dadada;
    padding: 15px 5px;
    background-color: #fff;
}

.placehold-text{
    display: block; /*span 으로 감싸서 크기영역을 블록요소로 만들어줘야한다*/
    position:relative;
    /* border: 1px solid #000; */
}

.placehold-text:before{ 
    position:absolute; /*before은 inline 요소이기 때문에 span으로 감싸줌 */
    right : 20px;
    top:13px;
    pointer-events: none; /*자체가 가지고 있는 pointer event 를 없애준다 */
}

.userpw{
    background:url(./images/images2/icon-01.png) no-repeat center right 15px;
    background-size: 20px;
    background-color: #fff;
}

.userpw-confirm{
    background:url(./images/images2/icon-02.png) no-repeat center right 15px;
    background-size: 20px;
    background-color: #fff;
}

.member-footer {
    text-align: center;
    font-size: 12px;
    margin-top: 20px;
}

.member-footer div a:hover{
    text-decoration: underline;
    color:#2db400
}

.member-footer div a:after{
    content:'|';
    font-size: 10px;
    color:#bbb;
    margin-right: 5px;
    margin-left: 7px;
    /*살짝 내려가 있기 때문에 위로 올려주기 위해 transform 사용하기*/
    display: inline-block;
    transform: translateY(-1px);

}

.member-footer div a:last-child:after{
    display: none;
}

@media (max-width:768px) {
    .member{
        width: 100%;
    }
}
 </style>
 <script type="text/javascript" src="../js/memberScript.js?v=1"></script>
 <title>
    윙크 : 회원가입
 </title>
 <body> 
<a href="/main/index" class="text-gray-800 text-xl font-bold hover:text-gray-700" >
            <img alt="이미지" src="../image/logo.png" 
                width="85" height="100" class="rounded-full"></a>
    <form action="/member/write" name="form1" method="get">
    <div class="member">
        <!-- 1. 로고 -->
       <!--  <img class="logo" src="./images/images2/logo-naver.png">  -->

        <!-- 2. 필드 -->
        <div class="field">
            <b>이름</b>
            <input type="text" name = "name" placeholder="이름">
        </div>
        <div class="field">
            <b>아이디</b>
            <span class="placehold-text">
            <input type="text" name = "id" placeholder="아이디">
            <input type="button" value="중복체크" 
                  onclick="checkId()">
                  
            </span>
        </div>
        <div class="field">
            <b>비밀번호</b>
            <input class="userpw" type="password" name = "pwd" placeholder="비밀번호">
        </div>
        <div class="field">
            <b>비밀번호 재확인</b>
            <input class="userpw-confirm" name="repwd" type="password">
        </div>

        <!-- 3. 필드(생년월일) -->
        <div class="field birth">
            <b>생년월일</b>
            <div>
                <input type="number" name="year" placeholder="년(4자)">                
                <select name="month">
                    <option value="">월</option>
                    <option value="">1월</option>
                    <option value="">2월</option>
                    <option value="">3월</option>
                    <option value="">4월</option>
                    <option value="">5월</option>
                    <option value="">6월</option>
                    <option value="">7월</option>
                    <option value="">8월</option>
                    <option value="">9월</option>
                    <option value="">10월</option>
                    <option value="">11월</option>
                    <option value="">12월</option>
                </select>
                <input type="number" placeholder="일" name="day">
            </div>
        </div>

        <!-- 4. 필드(성별) -->
        <div class="field gender">
            <b>성별</b>
            <div>
                <label><input type="radio" name="gender">남자</label>
                <label><input type="radio" name="gender">여자</label>
            </div>
        </div>

        <!-- 5. 이메일_전화번호 -->
        <div class="field">
            <b>본인 확인 이메일<small>(선택)</small></b>
            <input type="email" placeholder="선택입력">
        </div>
        
        <div class="field tel-number">
            <b>휴대전화</b>
            <select>
                <option value="">대한민국 +82</option>
            </select>
            <div>
                <input type="tel" placeholder="전화번호 입력">
                <input type="button" value="인증번호 받기">
            </div>
            <input type="number" placeholder="인증번호를 입력하세요">
        </div>

        <!-- 6. 가입하기 버튼 -->
        <input type="button" value="가입하기" onclick="inputCheck()">
      <hr>
        <!-- 7. 푸터 -->
        <div class="member-footer">
            <div>
                <a href="#none">이용약관</a>
                <a href="#none">개인정보처리방침</a>
                <a href="#none">책임의 한계와 법적고지</a>
                <a href="#none">회원정보 고객센터</a>
            </div>
            <span><a href="/main/index">wink</a></span>
        </div>
    </div>
    </form>
</body>
            </html>
        `;

		// 윈도우 창에 HTML 코드 삽입
		signUpWindow.document.write(signUpContent);
	});
});