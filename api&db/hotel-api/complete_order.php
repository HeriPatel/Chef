<?php
	include("conn.php");

	$tabName = $_GET['tabName'];
	$q = "select id from tab_status where name = '".$tabName."'";
	$r = mysqli_query($con,$q);
	$row = mysqli_fetch_assoc($r);
	$tabID = $row['id'];

	$query = "delete from ordereddetails where table_id = ".$tabID;
	$result = mysqli_query($con,$query);
	if ($result)
		echo json_encode("done");
	else
		echo json_encode("error");
?>