<?php	
	include("conn.php");
	
	$id = $_REQUEST['id'];
	$status = $_REQUEST['status'];
	
	$q = "update chef_status set status='".$status."' where id=$id";
	if($con->query($q))
		echo json_encode("success");
	else
		echo json_encode("failed");
?>