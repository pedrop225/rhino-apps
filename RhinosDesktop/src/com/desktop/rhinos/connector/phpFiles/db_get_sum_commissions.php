<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	SELECT SUM(commission)
						FROM Services 
						WHERE (idUser = :idUser) AND (idClient = :idClient)");
	
	$q->bindParam(':idUser', $_REQUEST['idUser']);
	$q->bindParam(':idClient', $_REQUEST['idClient']);
	
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;
	
	print(json_encode($output));
?>