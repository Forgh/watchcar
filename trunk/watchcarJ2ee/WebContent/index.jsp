<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Type in an address</title>
    </head>
    <body>
        <form method="POST" action ="sendAddress">
        	<fieldset>
        		<legend>Please type in the IP address you want to connect to : </legend>
        		
        		<label for="address">IP address : </label>
        		<input type="text" id="address" name="address" >
        	
        	</fieldset>
        	<input type="submit" value="OK" >
        </form>
    </body>
</html>