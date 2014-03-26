<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
	 <meta charset="utf-8" >
        <title>Remote control</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" >
		<script src="inc/jquery.min.js"></script>
		<script>
			var req;
			
			$(window).bind("beforeunload", function() { 
			    validate("close"); 
			});
			
			function validate(val) {
			   //var idField = document.getElementById("userid");
			   var url = "envoyerCommande?id=" + encodeURIComponent(val);
			   //alert(url);
			   if (typeof XMLHttpRequest != "undefined") {
			       req = new XMLHttpRequest();
			   } else if (window.ActiveXObject) {
			       req = new ActiveXObject("Microsoft.XMLHTTP");
			   }
			   req.open("GET", url, true);
			   //req.onreadystatechange = callback;
			   req.send(null);
			}
		</script>
	</head>
	
	<body>
		<div id="centeredCommands" >
			<p>
			  <input type="image" src="imgs/rotate_left.png" name="direction" value="rotate left" onclick="validate(this.value)">
		      <input id="rotate_right" type="image" src="imgs/rotate_right.png" name="direction" value="rotate right" onclick="validate(this.value)">
		   </p>
		   <p class="centering">   
		      <input type="image" src="imgs/upleft.png" name="direction" value="drift left" onclick="validate(this.value)">
		      <input type="image" src="imgs/forward.png" name="direction" value="forward" onclick="validate(this.value)">
		      <input type="image" src="imgs/upright.png" name="direction" value="drift right" onclick="validate(this.value)">
		   </p>
		   <p> 
		      <input class="rightleft" type="image" src="imgs/left.png" name="direction" value="left" onclick="validate(this.value)">
		      <input type="image" src="imgs/stop.png" name="direction" value="stop" onclick="validate(this.value)">
		      <input class="rightleft"  type="image" src="imgs/right.png" name="direction" value="right" onclick="validate(this.value)">
		   </p>
		   <p class="centering"> 
		      <input type="image" src="imgs/backleft.png" name="direction" value="drift back left" onclick="validate(this.value)">
		      <input type="image" src="imgs/back.png" name="direction" value="backward" onclick="validate(this.value)">
		      <input type="image" src="imgs/backright.png" name="direction" value="drift back right" onclick="validate(this.value)">
		 	</p>  
	    </div>
	     
	</body>
</html>