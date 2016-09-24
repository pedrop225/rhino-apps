<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("SELECT id, type, user, name, mail, p_profit
							FROM Login NATURAL JOIN Users
							WHERE id = :id");
	
	$q->bindParam(':id', $_REQUEST['id']);
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;

	print(json_encode($output));
?>