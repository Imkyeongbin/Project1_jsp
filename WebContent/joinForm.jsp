<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%
	String context = request.getContextPath();
%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<style type="text/css">
thead th {
	font-family: 'Nanum Gothic';
	background-color: #000000;
	color: white;
	text-align: center;
}

h5 {
	font-size: 14px;
	font-weight: bold;
}

input[type='radio'] {
	width: 16px;
	height: 16px;
	border: 1px solid darkgray;
	border-radius: 50%;
	background: #e6e6e6;
}

div, input, label { 
    font-size:12px;
}

#div1, #div2, #div3 {
   width:500px; margin:10px;
}

#div1 {
  height:120px;
  overflow:auto;
  border:1px solid black;
  padding:10px;
  margin-left:300px; margin-bottom:10px;
}                       
    
#div2 {
    margin-left:700px; margin-bottom:10px;
}
                          
</style>

<script src="js/bootstrap.js"></script>
<script type="text/javascript">
 // 관심항목 중복체크x
function check(chk){
    var obj = document.getElementsByName("interest");
    for(var i=0; i < obj.length; i++){
        if(obj[i] != chk){
            obj[i].checked = false;
        }
    }
}

   // 비밀번호 일치
   $(function() {
	 $(".alert-success").hide();
	 $(".alert-different").hide();
	 $("input").keyup(function(){
		 var user_password = $("#user_password").val();
	     var user_password1 = $("#user_password1").val();
	     if(user_password != "" || user_password1 != "") {
	    	 if(user_password == user_password1) {
	    		  $(".alert-success").show();
	    		  $(".alert-different").hide();
	    		  $("#submit").removeAttr("disabled");
	    		 
	    	 }else {
	    		 $(".alert-success").hide();
	    		 $(".alert-different").show();
	    		 $("#submit").attr("disabled" , "disabled");
	    		 
	    	 }
	     }
	     
	 }); 
   }); 
   
   // 아이디 중복체크
   function openIdChk(){
		 var user_email = $("#user_email").val();
		 if (user_email == "") {
			 alert("이메일을 입력하세요");
			 return;
		 }
 		console.log(user_email);
		/*  alert("user_email->"+user_email); */  
 		$.ajax({
			url:"<%=context%>/ajaxIdCheck.do",  
			data:{user_email : user_email},
			dataType:'text',
			success:function(data){
				/*  alert("ajaxIdCheck->"+data);   */
				 $('#msg1').html(data);      
			}
		});	    
   }
   
   // 닉네임 중복체크
      function NickNameChk(){
		 var user_nickname = $("#user_nickname").val();
		 if (user_nickname == "") {
			 alert("닉네임을 입력하세요");
			 return;
		 }
 		console.log(user_nickname);
 		$.ajax({
			url:"<%=context%>/ajaxNickNameCheck.do",  
			data:{user_nickname : user_nickname},
			dataType:'text',
			success:function(data){
				/*  alert("ajaxNickNameCheck->" + data); */
			  $('#msg2').html(data);
				if (data === '존재하는 닉네임입니다') {
				document.getElementById("submit").disabled = true;
				}else {
					document.getElementById("submit").disabled = false;
				}
			}
		});	    
   }
   

      function sample6_execDaumPostcode() {
          new daum.Postcode({
              oncomplete: function(data) {
                  // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                  // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                  // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                  var addr = ''; // 주소 변수
                  var extraAddr = ''; // 참고항목 변수

                  //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                  if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                      addr = data.roadAddress;
                  } else { // 사용자가 지번 주소를 선택했을 경우(J)
                      addr = data.jibunAddress;
                  }

                  // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                  if(data.userSelectedType === 'R'){
                      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                          extraAddr += data.bname;
                      }
                      // 건물명이 있고, 공동주택일 경우 추가한다.
                      if(data.buildingName !== '' && data.apartment === 'Y'){
                          extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                      }
                      // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                      if(extraAddr !== ''){
                          extraAddr = ' (' + extraAddr + ')';
                      }
                      // 조합된 참고항목을 해당 필드에 넣는다.
                      document.getElementById("sample6_extraAddress").value = extraAddr;
                  
                  } else {
                      document.getElementById("sample6_extraAddress").value = '';
                  }

                  // 우편번호와 주소 정보를 해당 필드에 넣는다.
                  document.getElementById('sample6_postcode').value = data.zonecode;
                  document.getElementById("sample6_address").value = addr;
                  // 커서를 상세주소 필드로 이동한다.
                  document.getElementById("sample6_detailAddress").focus();
              }
          }).open();
      }
      
    function Agree(b) {

   document.getElementById("btn1").disabled = b;

   }


  //관심항목 체크 안된 경우 서브밋 비활성화 
	$(document).ready(function(){
		if($("input[name='check_bc_num']:checked").length==0){
			$("#submit").attr("disabled", "disabled");
		}
	    $(".checkBoxId").change(function(){
	    	if($("input[name='check_bc_num']:checked").length==0){
	    		$("#submit").attr("disabled", "disabled");
	    	}
	    });
	});
	function checkCheck_bc_num(){
		if($("input[name='check_bc_num']:checked").length==0){
			alert("관심항목을 체크해주세요")
			return false;
		}
	}

      
    </script>

</head>
<body>
	<div class="container">
		<form action="joinForm.do" name="frm" enctype="multipart/form-data" method="post">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="text-align: center" colspan="3"><h4>회원정보입력</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th
							style="width: 110px; text-align: center; vertical-align: middle"><h5>프로필</h5>
						<td colspan="2"><input type="file" name="user_Profile" required="required"></td>
					</tr>
					

					<tr>
						<td style="width: 120px;"><h5>아이디(이메일)</h5>
						<td><input class="form-control" type="email" id="user_email"
							name="user_email" maxlength="50" placeholder="이메일을 입력하세요"
							required="required">
							<span id="msg1"></span><p>
							
						</td>
						<td style="width: 110px;">
						<button id ="chk1" class="btn btn-primary" style="background-color: #000000; border-color: #A2A2A2;"
								type="button" onclick="openIdChk();">중복체크</button></td>

					</tr>

					<tr>
						
						<td style="width: 110px;"><h5>비밀번호</h5></td>
						<td colspan="2"><input class="form-control" type="password"
							id="user_password" name="user_password" maxlength="20"
							required="required"></td>
					</tr>
				
					
					<tr>
						<td style="width: 110px;"><h5>비밀번호 확인</h5></td>
						<td colspan="2"><input class="form-control" type="password"
							id="user_password1" name="user_password1" maxlength="20"
							required="required">
							<div class="alert-success">
						           비밀번호가 일치합니다.
					       </div>
					       <div class="alert-different">
					  	          비밀번호가 불일치합니다.
					       </div>
							
						</td>
					
					</tr> 
					
			

					<tr>
						<td style="width: 110px;"><h5>이름</h5></td>
						<td colspan="2"><input class="form-control" type="text"
							id="user_name" name="user_name" maxlength="20"
							required="required"></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>닉네임</h5>
						<td><input class="form-control" type="text" id="user_nickname"
							name="user_nickname" maxlength="20" placeholder="1~10자까지 입력하세요"
							required="required">
							<span id="msg2"></span><p>
							
						</td>
						<td style="width: 110px;">
						<button id ="chk2" class="btn btn-primary" style="background-color: #000000; border-color: #A2A2A2;"
								type="button" onclick="NickNameChk();">중복체크</button></td>

					</tr>

					<tr>
						<td style="width: 110px;"><h5>생년월일</h5></td>
						<td colspan="2"><input class="form-control" type="date"
							id="user_birth" name="user_birth" maxlength="15"
							placeholder="YYYY-MM-DD"
							title="생일을 입력하세요" required="required"></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>전화번호</h5></td>
						<td colspan="2"><input class="form-control" type="text"
							id="user_phone" name="user_phone" maxlength="15"
							pattern="\d{2,3}-\d{3,4}-\d{4}" placeholder="xxx-xxxx-xxxx"
							title="3자리-4자리-4자리" required="required"></td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>핀번호</h5></td>
						<td colspan="2"><input class="form-control" type="text"
							id="user_pin" name="user_pin"  maxlength ="6" required="required"></td>
					</tr>
					
					
					<tr>
						<td style="width: 110px; text-align: center; vertical-align: middle"><h5>주소</h5>
						<td><input class="form-control" type="text" id="sample6_postcode" style="margin: 5px;"
							       name="zip" value="${zip }" placeholder="우편번호" required="required">
						<input class="form-control" type="text" id="sample6_address" placeholder="주소" style="margin: 5px; "
						       name = "address" value="${address }">
						 
						<input class="form-control" type="text" id="sample6_extraAddress" placeholder="참고항목" style="margin: 5px;"
						       name="referenceItem" value="${referenceItem }">
						       
					    <input class="form-control" type="text" id="sample6_detailAddress" placeholder="상세주소" style="margin: 5px;"
						       name = "addressInDetail" value="${addressInDetail }">                
						
						</td>
					
				       
						<td style="width: 110px;  align: center;">
						 <input class="btn btn primary" 
						    style="background-color: #000000; border-color: #000000; color: white; "
                                type="button" onclick="sample6_execDaumPostcode()" value="주소검색"></td>
                      
					</tr>
				    
					
					<tr>
						<td style="width: 110px;"><h5>성별</h5></td>
						<td colspan="2">
							<div class="form-group" style="text-align: center; margin: 0 auto;">
								<input type="radio" name="user_gender" value="female" >여자
								<input type="radio" name="user_gender" value="male"> 남자
							</div>
						</td>
					</tr>

					<tr>
						<td style="width: 110px;"><h5>관심항목</h5></td>
						<td colspan="2">
							<div class="form-group" style="text-align: center; margin: 0 auto;">
							 <!--  <div class="btn-interest active" data-toggle="buttons"> -->
								    <div class="btn-interest active">
									<label class="digital"> <input type="checkbox"
										name="interest" id="digital" value="100" onclick="check(this)" checked="checked"> 디지털/가전
									</label> <label class="btn-interest"> <input type="checkbox"
										name="interest" id="furniture" value="200" onclick="check(this)"> 가구/인테리어
									</label> <label class="btn-interest"> <input type="checkbox"
										name="interest" id="sport" value="300" onclick="check(this)"> 스포츠/레저
									</label> <label class="btn-interest"> <input type="checkbox"
										name="interest" id="beauty" value="400" onclick="check(this)"> 뷰티/미용
									</label> <label class="btn-interest"> <input type="checkbox"
										name= "interest" id="goods" value="500" onclick="check(this)"> 여성잡화
									</label> <label class="btn-interest"> <input type="checkbox"
										name="interest" id="cloth" value="600" onclick="check(this)"> 의류
									</label>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			 
			 <table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd">
				<tr><td style="width: 110px; text-align: center; vertical-align: middle"><h5>이용약관</h5></td>
				  <!-- <td style= "width:300px; height:300px; position:absolute; left:50%; top:100%; margin-left:-150px; margin-top:-150px;">
 -->				  
			 </table>
			
			  
			   <div id="div1">
                   당근Bay 온라인 서비스 이용약관<p>
                   제1장 총칙<p>
                   제1조 (목적)<p>
                   이 약관은 주식회사 당근Bay은(이하 "당사"라고 합니다)가 인터넷사이트를 통하여 제공하는 경매서비스, 쇼핑몰서비스, 정보서비스, 기획서비스(이하 "서비스"라고 합니다) 등과 관련하여
                   당사와 회원 간의 권리와 의무, 책임사항 및 회원의 서비스이용 절차에 관한 사항을 규정함을 목적으로 합니다.<p>
                   
                   제2조 (약관의 명시, 효력 및 변경)<p>
                   ① 당사는 이 약관을 통해 서비스를 이용하고자 하는 자와 회원이 알 수 있도록 서비스가 제공되는 당근Bay 사이트 화면에 게시합니다.<p>
                   ② 당사가 이 약관을 개정하는 경우에는 개정된 약관의 적용일자 및 개정사유를 명시하여 그 적용일자 7일 이전부터 적용일자 전일까지 위 ①항의 방법으로 공지합니다.<p>
                   ③ 이 약관은 당사와 회원 간에 성립되는 서비스 이용계약의 기본약정입니다.<p>
                   ④ 당사는 필요한 경우 특정 서비스에 관하여 적용될 사항(이하 "개별약관"이라고 합니다)을 정하여 미리 공지할 수 있습니다. 회원이 이러한 개별약관에 동의하고 특정 서비스를 이용하는 경우에는 개별약관이 우선적으로 적용되고, 이 약관은 보충적인 효력을 갖습니다. 개별약관의 변경에 관해서는 위 ②항을 준용합니다.<p>		
			 
			      제3조 (관련 법령과의 관계)
                  ① 이 약관 또는 개별약관에서 정하지 않은 사항은 전기통신사업법, 전자거래기본법, 정보통신망이용촉진 및 정보보호 등에 관한 법률, 전자상거래 등에서의 소비자보호에 관한 법률, 개인정보 보호법, 부가가치세법 등 관련법령의 규정과 일반적인 상관례에 의합니다.<p>
                  ② 회원은 당사가 제공하는 서비스를 이용함에 있어서 방문판매 등에 관한 법률(예시: 상품인도서, 표시광고, 청약철회, 배송 등 통신판매에 관한 규정), 전자거래기본법(예시: 소비자보호에 관한 규정), 소비자보호법(예시: 사업자의 의무에 관한 규정), 표시광고의 공정화에 관한 법률(예시: 물품 상세설명에 관련한 표시, 기재사항), 정보통신망이용촉진 및 정보보호 등에관한 법률(예시: 개인정보 보호에 관한 규정) 등 관련법령을 준수하여야 하며, 이 약관의 규정을 들어 관련 법령 위반에 대한 면책을 주장할 수 없습니다.<p>
			 
			      제4조 (서비스의 종류)<p>
                  당사가 회원에게 제공하는 서비스는 다음과 같습니다.<p>

                 ① 경매 및 쇼핑몰서비스 : 당사가 코베이옥션 사이트를 통하여 회원 상호간에 물품 매매거래가 이루어질 수 있는 사이버 거래장소를 온라인으로 제공하는 서비스 및 관련 부가서비스 일체를 말합니다.<p>
                 ② 정보서비스: 경매 및 쇼핑몰 서비스이외에 당사가 코베이옥션 사이트를 통하여 회원에게 온라인으로 제공하는 정보서비스, Community 등의 인터넷서비스를 말합니다.<p>
                 ③ 기획서비스 : 당사가 기획하여 진행하는 모든 경매 및 실물 전시, 판매 등의 서비스를 말합니다.<p>
                 ④ 기타서비스 : 기타 당사가 제공하는 서비스를 말합니다.<p>
			  </div>
			
			     <div id="div2">

                    <input type="checkbox" id="agree" name="agree" required="required"><label for="rb1">&nbsp약관에 동의합니다.</label>

             </div>
            

			<input type="submit" value="가입하기" class="btn btn-primary pull-right" id="submit"
				style="background-color: #000000; border-color: #A2A2A2;">

		</form>
	</div>

</body>
</html>