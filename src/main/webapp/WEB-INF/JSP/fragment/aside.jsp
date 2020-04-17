<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/28/2019
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags" %>


<div class="visible-xs-block xs-option-container">
	<a class="pull-right" data-toggle="collapse" href="#productCatalog">Product catalog <span class="caret"></span></a>
	<a data-toggle="collapse" href="#findProducts">Find products <span class="caret"></span></a>
</div>
<!-- Search form -->
<form class="search" action="/search">
	<div id="findProducts" class="panel panel-success collapse">
		<div class="panel-heading">Find products</div>
		<div class="panel-body">
			<div class="input-group">
				<input type="text" name="query" class="form-control" placeholder="Search query" value="${searchForm.query}">
				<span class="input-group-btn">
                    <a id="goSearch" class="btn btn-default">Go!</a>
                  </span>
			</div>
			<div class="more-options">
				<a data-toggle="collapse" href="#searchOptions">More filters <span class="caret"></span></a>
			</div>
		</div>
		<div id="searchOptions" class="collapse ${(!searchForm.categoriesEmpty or !searchForm.producersEmpty) ? 'in' : ''} ">
			<ishop:category-filter categories="${CATEGORY_LIST}" searchForm="${searchForm}" />
			<ishop:producer-filter producers="${PRODUCER_LIST}"  searchForm="${searchForm}" />
		</div>
	</div>
</form>
<!-- /Search form -->
<!-- Categories -->
<div id="productCatalog" class="panel panel-success collapse">
	<div class="panel-heading">Product catalog</div>
	<div class="list-group">
	<c:forEach var="c" items="${CATEGORY_LIST}">
		<a href="/products${c.url}" class="list-group-item ${selectedCategoryUrl == c.url ? 'active' : ''}"> <span class="badge">${c.productCount}</span> ${c.name}</a>
		<%--<a href="/products/ebook" class="list-group-item"> <span class="badge">78</span> E-book</a>--%>
		<%--<a href="/products/mp3-player" class="list-group-item"> <span class="badge">75</span> Mp3-player</a>--%>
		<%--<a href="/products/notebook" class="list-group-item"> <span class="badge">110</span> Notebook</a>--%>
		<%--<a href="/products/phone" class="list-group-item"> <span class="badge">113</span> Phone</a>--%>
		<%--<a href="/products/smartphone" class="list-group-item"> <span class="badge">216</span> Smartphone</a>--%>
		<%--<a href="/products/smartwatch" class="list-group-item"> <span class="badge">95</span> Smartwatch</a>--%>
		<%--<a href="/products/tablet" class="list-group-item"> <span class="badge">211</span> Tablet</a>--%>
	</c:forEach>
	</div>
</div>
<!-- /Categories -->