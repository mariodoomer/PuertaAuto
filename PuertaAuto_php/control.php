<!DOCTYPE HTML>
<html>
    <head>
		<title>Control de Puerta</title>
    </head>
	<body>
		<h1>Puerta principal</h1>
		<p>controles.</p>
		<form method="POST" action="control.php">
			<table border=1 >
				<tr><td>Abrir</td><td><input type="radio" name="puerta" value="1"  onclick="submit();"></td></tr>
				<tr><td>Cerrar</td><td><input type="radio" name="puerta" value="0"  onclick="submit();"></td></tr>
			</table>
		</form>
	</body>
</html>
<?php
	$estado=$_POST['puerta'];
	echo json_encode($estado);
?>