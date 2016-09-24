<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("SELECT id, type, user, name, mail, p_profit
					  FROM (Users NATURAL JOIN Login) INNER JOIN Structure
					  ON Users.id = Structure.child
					  WHERE id IN (SELECT child FROM Structure WHERE parent= :parent)");
	
	$q->bindParam(':parent', $_REQUEST['parent']);
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;
	
	print(json_encode($output));
?>