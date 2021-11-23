<?php	
	include("conn.php");

	class response
	{
		public $Categor_name;
		public $Categor_img;
	}
	
	$q = "select * from category";

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	
	$row = mysqli_num_rows($result);
	
	  while($row = mysqli_fetch_assoc($result)) {
		 $r = new response();
		 $r->Categor_name = $row['name'];
		 $r->Categor_img = $row['image'];
		 $arr[$i] = $r;
		 $i = $i + 1;
	  }
	  echo json_encode($arr);
	
?>