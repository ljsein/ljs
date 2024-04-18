function checkEmailInput() {
   var frm = document.form1;
   
   if (!frm.client_email.value) {
      alert("이메일을 입력해주세요");
      frm.client_email.focus();
      return false;
   }
   
}


function checkEmail() {
   var frm = document.form1;
   // 이름 입력 검사
    if (!frm.verification_code.value || !isNaN(frm.verification_code.value)) {
      alert("인증코드만 입력해주세요");
      frm.client_name.focus();
      return false;
   }
   
}

function checkId() {
   var sId = document.form1.client_id.value;
   
   if(!sId) {
      alert("먼저 아이디를 입력하세요.")
   } else {
      window.open("checkId?client_id=" + sId, "", "width=350 height=100 left=500 top=200");
   }
}

function inputCheck_login() {
   var frm = document.form2; //form 객체 얻어오기
   // 아이디 입력 검사
   if (!frm.client_id.value) {
      alert("아이디를 입력해주세요");      
      frm.client_id.focus();
      return false;
   }

   // 비밀번호 입력 검사
   if (!frm.client_pwd.value) {
      alert("비밀번호 입력해주세요");
      frm.client_pwd.focus();
      return false;
   }

   if (confirm("입력하신 내용이 맞습니까?")) {
      frm.submit();
   }

}

function inputCheck_login1() {
   var frm = document.form3; //form 객체 얻어오기
   // 아이디 입력 검사
   if (!frm.employee_id.value) {
      alert("아이디를 입력해주세요");      
      frm.employee_id.focus();
      return false;
   }

   // 비밀번호 입력 검사
   if (!frm.employee_pwd.value) {
      alert("비밀번호 입력해주세요");
      frm.employee_pwd.focus();
      return false;
   }

   if (confirm("입력하신 내용이 맞습니까?")) {
      frm.submit();
   }

}


function inputCheck() {
   var frm = document.form1;
   // 이름 입력 검사
    if (!frm.client_name.value || !isNaN(frm.client_name.value)) {
      alert("이름을 입력해주세요");
      frm.client_name.focus();
      return false;
   }
   // 아이디 입력 검사
   if (!frm.client_id.value) {
      alert("아이디를 확인해주세요")
      frm.client_id.focus();
      return false;
   }
   // 비밀번호 입력 검사
   if (!frm.client_pwd.value) {
      alert("비밀번호를 확인해주세요")
      frm.pwd.focus();
      return false;
   }
   // 재확인 입력 검사
   if (!frm.repwd.value) {
      alert("재확인을 입력해주세요")
      frm.repwd.focus();
      return false;
   }
   // 비밀번호와 재확인 번호가 같은 지 검사
   if (frm.client_pwd.value != frm.repwd.value) {
      alert("비밀번호가 일치하지 않습니다.");
      frm.repwd.value = "";
      frm.repwd.focus();
      return false;
   }
   
   if (!frm.client_email.value) {
      alert("이메일을 입력해주세요");
      frm.client_email.focus();
      return false;
   }
   //생년월일 입력 검사
   if(!frm.client_birthyear.value){
      alert("연도 입력하세요");
      frm.client_birthyear.focus();
      return false;   
   }
   if(frm.client_birthmonth.index == 0){
      alert("월을 입력하세요");
      frm.client_birthmonth.focus();
      return false;   
   }
   if(!frm.client_birthday.value){
      alert("일을 입력하세요");
      frm.client_birthday.focus();
      return false;
   }
   //휴대전화 입력 검사
   if(!frm.client_tel.value){
      alert("휴대전화 입력하세요");
      frm.client_tel.focus();
      return false;
   }

   if (confirm("입력하신 내용이 맞습니까?")) {
      frm.submit();
   }
}