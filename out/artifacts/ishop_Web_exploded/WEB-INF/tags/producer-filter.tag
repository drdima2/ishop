<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="producers" required="true" type="java.util.Collection" %>
<%@ attribute name="searchForm" required="true" type="net.devstudy.ishop.form.SearchForm" %>
<div class="panel-heading">Producers filters</div>
<div class="panel-body producers">
	<label> <input type="checkbox" id="allProducers"> All </label>
	<c:forEach var="p" items="${producers}">
	<div class="form-group">
		<div class="checkbox">
			<label>
				<input type="checkbox" name="producer" value="${p.id}" class="search-option" ${searchForm.producers.contains(p.id) ? 'checked' :''}>${p.name} (${p.productCount}) </label>
		</div>
	</div>
	</c:forEach>
</div>