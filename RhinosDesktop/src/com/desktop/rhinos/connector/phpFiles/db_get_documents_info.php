<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	SELECT id, name, date
							FROM Documents
							WHERE idService = :idService
							ORDER BY date");
	
	$q->bindParam(':idService', $_REQUEST['idService']);
	
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;
	
	print(json_encode($output));
?>