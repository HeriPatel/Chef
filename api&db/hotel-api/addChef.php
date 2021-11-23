<?php	
	include("conn.php");
	
	$id = $_REQUEST['id'];
	$chef_id =$_REQUEST['chef_id'];
	
	$q = "update ordereddetails set chef_id=$chef_id where id=$id";
	
	if($con->query($q))
		echo json_encode("success");
	else
		echo json_encode("failed");
?>