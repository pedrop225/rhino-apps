<?php
	include 'db_settings.php';

	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	SELECT id FROM Login NATURAL JOIN Users 
						WHERE user=:user OR mail=:mail");
	
	$q->bindParam(':user', $_REQUEST['user']);
	$q->bindParam(':mail', $_REQUEST['mail']);
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;
	
	print(json_encode($output));
?>