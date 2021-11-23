<?php
	include("conn.php");
	
	$passwd = md5($_REQUEST['passwd']);
	$phone = $_REQUEST['phone'];
	
	$q = "update chef_details set password='$passwd' where phone_num=$phone";
	if($con->query($q))
		echo json_encode("success");
	else
		echo json_encode("failed");
?>