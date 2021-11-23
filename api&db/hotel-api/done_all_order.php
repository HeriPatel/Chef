<?php	
	include("conn.php");
	
	// $id = $_REQUEST['id'];
	$chef_id =$_REQUEST['chef_id'];
	
	/*$q1 = "select * from ordereddetails where chef_id = ".$id;
	$r1 = mysqli_query($con,$q1);
	$row1 = mysqli_num_rows($r1);*/

	// if($row1 > 0){
		$q = "update ordereddetails set chef_id=NULL where chef_id=$chef_id";
		if($con->query($q))
			echo json_encode("success");
		else
			echo json_encode("failed");
	// }
?>