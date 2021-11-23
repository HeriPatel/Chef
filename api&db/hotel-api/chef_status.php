<?php	
	include("conn.php");

	$id = $_REQUEST['id'];

	$q = "select * from chef_status where id=$id";

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	$row = mysqli_num_rows($result);

	if($row = mysqli_fetch_assoc($result))
		echo json_encode($row ['status']);
	else
		echo "Error!";
?>