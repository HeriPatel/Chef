<?php
	include("conn.php");
	
	$b = $_REQUEST['key'];
	$id = $_REQUEST['id'];
	
	/*if($id = "")
		$q = "insert into chef_details (fcm_key) values ('$b')";
	else*/
	$q = "update chef_details set fcm_key='$b' where id=$id";
	if($con->query($q))
		echo json_encode("success");
	else
		echo json_encode("failed");
?>