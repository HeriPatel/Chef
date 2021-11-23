<?php	
	include("conn.php");

	class response
	{
		public $tabName;
		public $tabStatus;
		// public $requiredTime;
	}
	
	$q = "select * from tab_status";

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	$row = mysqli_num_rows($result);

	  while($row = mysqli_fetch_assoc($result)) {
		 $r = new response();
		 $r->tabName = $row['name'];
		 $r->tabStatus = $row['status'];
		 // $a = new DateTime($row['ending time']);
		 // $b = new DateTime($row['starting time']);
		 // $t = $a->diff($b);
		 // $r->requiredTime = $t->format("%h:%i:%s");
		 $arr[$i] = $r;
		 $i = $i + 1;
	  }
	  echo json_encode($arr);
	
?>