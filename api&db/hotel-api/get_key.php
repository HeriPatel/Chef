<?php
	include("conn.php");
	
	$id = $_REQUEST['id'];
	$q = "select * from chef_details where id=$id";
		
	$rs = $con->query($q);
	
	if($r = mysqli_fetch_assoc($rs))
		echo json_encode($r['fcm_key']);
	else
		echo "fail...";
?>