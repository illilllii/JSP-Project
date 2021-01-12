<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<h2>회원 정보</h2>
	<c:if test="${!empty sessionScope.principal.id}">
		<p>${sessionScope.principal.username}로 접속 중.</p>
	</c:if>

	<table class="table">
		<thead>
			<tr>
				<th>ID</th>
				<th>USERNAME</th>
				<th>PASSWORD</th>
				<th>EMAIL</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr id="user-${user.id}">
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.password}</td>
					<td>${user.email}</td>
					<td><c:choose>
							<c:when test="${sessionScope.principal.username=='admin'}">
								<button onclick="deleteUser(${user.id})" type="button"
									class="btn btn-danger">삭제</button>
							</c:when>
							<c:otherwise>
								<c:if test="${user.id == sessionScope.principal.id}">
									<button onclick="deleteUser(${user.id})" type="button"
										class="btn btn-danger">삭제</button>
								</c:if>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script>
	function deleteUser(id) {
		$.ajax({
			type: "post",
			url: "/serverProject/user?cmd=delete&id="+id,
			dataType: "json"
			
		}).done(function(result) {
			if(result.statusCode == 1) {
				console.log(result);
				$("#user-"+id).remove();
				location.reload();
			}
		});
	}
</script>
</body>
</html>