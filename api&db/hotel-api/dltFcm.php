<?php	
	include("conn.php");
	
	$id = $_REQUEST['id'];
	
	$q = "update chef_details set fcm_key=NULL where id=$id";
	if($con->query($q))
		echo json_encode("success");
	else
		echo json_encode("failed");
?>