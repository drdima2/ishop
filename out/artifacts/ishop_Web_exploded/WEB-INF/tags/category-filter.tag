<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="categories" required="true" type="java.util.Collection" %>
<%@ attribute name="searchForm" required="true" type="net.devstudy.ishop.form.SearchForm" %>

<div class="panel-heading">Category filters</div>
<div class="panel-body categories">
	<label> <input type="checkbox" id="allCategories"> All </label>

	<c:forEach var="c" items="${categories}">
		<div class="form-group">
			<div class="checkbox">
				<label>
					<input type="checkbox" name="category" value="${c.id}" class="search-option"
					${searchForm.categories.contains(c.id) ? 'checked' :''}
					>
						${c.name} (${c.productCount})
				</label>
			</div>
		</div>
	</c:forEach>
</div>