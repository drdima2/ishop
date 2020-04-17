<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/28/2019
  Time: 11:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<c:forEach var="p" items="${products}">
	<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-2">
		<!-- PRODUCT DATA -->
		<div id="product${p.id}" class="panel panel-default product">
			<div class="panel-body">
				<div class="thumbnail">
					<img src="/media/${p.imageLink}" alt="${p.name}">
					<div class="desc">
						<div class="cell">
							<p><span class="title">Details</span>${p.description}</p>
						</div>
					</div>
				</div>
				<h4 class="name">${p.name}</h4>
				<div class="code">Code: ${p.id}</div>
				<div class="price">$ ${p.price}</div>
				<a class="btn btn-primary pull-right buy-btn" data-id-product="${p.id}">Buy</a>
				<div class="list-group">
						<span class="list-group-item">
							<small>Category:</small>
								<span class="category">${p.category}</span>
						</span>
					<span class="list-group-item">
							<small>Producer:</small>
								<span class="producer">${p.producer}</span>
						</span>
				</div>
			</div>
		</div>
		<!-- /PRODUCT DATA -->
	</div>
</c:forEach>

