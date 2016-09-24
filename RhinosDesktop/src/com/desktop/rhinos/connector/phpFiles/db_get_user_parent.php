<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = mysql_query("	SELECT parent, name, mail, p_profit
						FROM Structure INNER JOIN Users ON Structure.parent = Users.id 
						WHERE child = :child");
	
	$q->bindParam(':child', $_REQUEST['child']);
	$q->execute();
	
	while ($e = $q->fetch(PDO::FETCH_ASSOC))
		$output[] = $e;

	print(json_encode($output));
?>