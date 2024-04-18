function checkBoardWrite() {
	var frm = document.form1;
	alert("test`1");
	// 입력검사
	if (!frm.board_title.value) {
		alert("제목을 입력하세요.");
		frm.subject.focus();		
		return false;
	}
	if (!frm.board_content.value) {
		alert("내용을 입력하세요.");
		frm.content.focus();		
		return false;
	}
	frm.submit(); // 데이터 전송 
}

function checkBoardModify() {
	var frm = document.form1;
	alert("test`2");
	var subject = frm.board_title.value;
	var content = frm.board_content.value;
	if(!subject) {
		alert("제목을 입력하세요");
		frm.board_title.focus();
		return false
	}
	if(!content) {
		alert("내용을 입력하세요");
		frm.board_content.focus();
		return false
	}
	frm.submit();
}