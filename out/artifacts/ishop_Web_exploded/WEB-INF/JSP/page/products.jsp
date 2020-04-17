<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/28/2019
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="productList" data-page-count="${pageCount}" data-page-number="1">
	<div class="row">
		<jsp:include page="../fragment/product-list.jsp"/>
	</div>
	<c:if test="${pageCount >1}">
		<div class="text-center hidden-print">
			<img id="loadMoreIndicator" src="/static/img/loading.gif" class="hidden" alt="Loading...">
			<a id="loadMore" class="btn btn-success">Load more products</a>
		</div>
	</c:if>
</div>
<ishop:add-product-popup/>
