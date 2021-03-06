<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	SELECT DISTINCT Clients.id, b_date, name, Clients.tlf_1
						FROM Clients, Services 
						WHERE (Clients.id = Services.idClient) and (idUser = :idUser)");
	
	$q->bindParam(':idUser', $_REQUEST['idUser']);
	
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;

	print(json_encode($output));
?>