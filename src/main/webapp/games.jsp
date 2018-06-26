<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">


    <link href="${contextPath}/css/product.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

  <div class="navbar navbar-inverse navbar-static-top">

   <div class="container">

        <div class="collapse navbar-collapse navHeaderCollapse">
          <ul class="nav navbar-nav navbar-right">
                <c:forEach items="${names}" var="name">
                    <li class="dropdown">
                      <a href="${contextPath}?game=${name}" class="dropdown-toggle" data-toggle="dropdown">${name}</a>
                    </li>
                </c:forEach>
          </ul>
        </div>
      </div>
    </div>

<style>
  #custom-search-form {
        margin:0;
        margin-top: 5px;
        padding: 0;
    }

    #custom-search-form .search-query {
        padding-right: 3px;
        padding-right: 4px \9;
        padding-left: 3px;
        padding-left: 4px \9;
        /* IE7-8 doesn't have border-radius, so don't indent the padding */

        margin-bottom: 0;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        -webkit-transition: width  0.2s ease-in-out;
    -moz-transition:width  0.2s ease-in-out;
    -o-transition: width  0.2s ease-in-out;
    transition: width  0.2s ease-in-out;
    }

    #custom-search-form button {
        border: 0;
        background: none;
        /** belows styles are working good */
        padding: 2px 5px;
        margin-top: 2px;
        position: relative;
        left: -28px;
        /* IE7-8 doesn't have border-radius, so don't indent the padding */
        margin-bottom: 0;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
    }

    .search-query:focus + button {
        z-index: 3;
    }
    .search-query:focus{
        width: 260px;
    }

    col-item .price-details h1 {
             width : 250px;
             overflow:hidden;
             display:inline-block;
             text-overflow: ellipsis;
             white-space: nowrap;
             font-size: 14px;
             line-height: 20px;
             margin: 0;
                float:left;
           }

</style>
<body>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<div class="container">
	<div class="row">
        <c:forEach items="${games}" var="game">
        <div class="col-sm-3">
        	<article class="col-item">
        		<div class="photo">
        			<a href="#"> <img src="${game.img}"  width="190" height="230" alt="Product Image" /> </a>
        		</div>
        		<div class="info">
        			<div class="row">
        				<div class="price-details col-md-6">
        					<h1>${game.name}</h1>
        					<span class="price-new"><br>${game.price}</span>
        				</div>
        			</div>
        			<div class="separator clear-left">
        				<p class="btn-add">
        					<a href="${game.href}" class="hidden-sm">Look at it</a>
        				</p>
        			</div>
        			<div class="clearfix"></div>
        		</div>
        	</article>
        </div>
        </c:forEach>
	</div>
</div
</body>