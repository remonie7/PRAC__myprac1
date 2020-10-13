$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e){
	e.preventDefault(); //디폴트 동작(서버로 데이터 보내는) 막아버리기
	console.log("click me"); //크롬 개발자도구-콘솔에서 출력 확인
	
	
	var queryString = $(".answer-write").serialize();	//값 가져오기?
	console.log("query : " + queryString); //contents 값이 출력됨
	
	var url = $(".answer-write").attr("action"); //answer-write의 action 이란 속성값 가져옴
	console.log("url : " + url);
	$.ajax(
		{type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess}
	);
	
}

function onError(){

}

function onSuccess(data, status){ 
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
	$(".qna-comment-slipp-articles").prepend(template);
	
	$(".answer-write textarea").val("");
}

$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e){
	e.preventDefault();
	var deleteBtn = $(this);
	var url = deleteBtn.attr("href");
	console.log("url : "+url);
	
	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function (xhr, status){
			console.log("error");
		},
		success : function (data, status) {
			console.log(data);
			if (data.valid){
			deleteBtn.closest("article").remove();
		} else{
			alert(data.errorMessage);
		}
		}
	});	
}


String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};