<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/dynamic_list_helper.js" type="text/javascript"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Customer List</h3>
        <div style="border:1px solid #eaeaea;padding:20px;width:400px">
            ${message}
        </div>
        <form:form action="editcustomerlistcontainer" modelAttribute="customerListContainer" method="post" id="customerListForm">
            <table>
                <thead>
                    <tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
                    </tr>
                </thead>
                <tbody id="customerListContainer">
                    <c:forEach items="${customerListContainer.customerList}" var="customer" varStatus="i" begin="0" >
                        <tr class="customer">    
                            <td><form:input path="customerList[${i.index}].id" id="id${i.index}" /></td>
                            <td><form:input path="customerList[${i.index}].firstName" id="firstName${i.index}" /></td>
                            <td><form:input path="customerList[${i.index}].lastName" id="lastName${i.index}" /></td>
                            <td><form:input path="customerList[${i.index}].email" id="email${i.index}" /></td>
                            <%--
                            <td><input type="text" name="customerList[].name" value="${customer.name}" /></td>
                            <td><input type="text" name="customerList[].age" value="${customer.age}" /></td>
                            --%>
                            <td><a href="#" class="removecustomer">Remove customer</a></td>
                        </tr>
                    </c:forEach>
                    <%-- 
                        IMPORTANT 
                        There must always be one row.
                        This is to allow the JavaScript to clone the row.
                        If there is no row at all, it cannot possibly add a new row.

                        If this 'default row' is undesirable 
                            remove it by adding the class 'defaultRow' to the row
                        I.e. in this case, class="customer" represents the row.
                        Add the class 'defaultRow' to have the row removed.
                        This may seem weird but it's necessary because 
                        a row (at least one) must exist in order for the JS to be able clone it.
                        <tr class="customer"> : The row will be present
                        <tr class="customer defaultRow"> : The row will not be present
                    --%>
                    <c:if test="${customerListContainer.customerList.size() == 0}">
                        <tr class="customer defaultRow">    
                            <td><input type="text" name="customerList[].id" value="" /></td>
                            <td><input type="text" name="customerList[].firstName" value="" /></td>
							<td><input type="text" name="customerList[].lastName" value="" /></td>
							<td><input type="text" name="customerList[].email" value="" /></td>
							<td><a href="#" class="removecustomer">Remove customer</a></td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            <input type="submit" value="Save" id="submit" />&nbsp;&nbsp;
            <a href="#" id="addcustomer">Add customer</a>&nbsp;&nbsp;
            <a href="?f=">Reset List</a>
        </form:form>

        <script type="text/javascript">
            function rowAdded(rowElement) {
                //clear the imput fields for the row
                $(rowElement).find("input").val('');
                //may want to reset <select> options etc

                //in fact you may want to submit the form
                saveNeeded();
            }
            function rowRemoved(rowElement) {
                saveNeeded();
                alert( "Removed Row HTML:\n" + $(rowElement).html() );
            }

            function saveNeeded() {
                $('#submit').css('color','red');
                $('#submit').css('font-weight','bold');
                if( $('#submit').val().indexOf('!') != 0 ) {
                    $('#submit').val( '!' + $('#submit').val() );
                }
            }

            function beforeSubmit() {
                alert('submitting....');
                return true;
            }

            $(document).ready( function() {
                var config = {
                    rowClass : 'customer',
                    addRowId : 'addcustomer',
                    removeRowClass : 'removecustomer',
                    formId : 'customerListForm',
                    rowContainerId : 'customerListContainer',
                    indexedPropertyName : 'customerList',
                    indexedPropertyMemberNames : 'id,firstName,lastName,email',
                    rowAddedListener : rowAdded,
                    rowRemovedListener : rowRemoved,
                    beforeSubmit : beforeSubmit
                };
                new DynamicListHelper(config);
            });
        </script>

    </body>
</html>