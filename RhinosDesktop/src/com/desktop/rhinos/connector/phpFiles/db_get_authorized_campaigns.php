<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = mysql_query("	SELECT campaign
						FROM Permissions 
						WHERE idUser = :idUser ");
	
	$q->bindParam(':idUser', $_REQUEST['idUser']);
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;

	print(json_encode($output));
?>