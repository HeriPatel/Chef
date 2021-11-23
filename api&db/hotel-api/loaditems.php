<?php
	include("conn.php");
	$subCatName = $_GET['subCatName'];

	class response
	{
		public $id;
		public $name;
		public $price;
		public $des;
		public $img;
		public $qnty;
	}
	
	$q2 = 'select id from sub_category where name = "'.$subCatName.'"';
	$result2 = mysqli_query($con,$q2);
	$row2 = mysqli_fetch_assoc($result2);

	$q = "select * from items where sub_cat_id=".$row2['id'];

	$result=mysqli_query($con,$q);
	$arr = array();
	$i = 0;
	
	$row = mysqli_num_rows($result);
	
	  while($row = mysqli_fetch_assoc($result)) {
		 $r = new response();
		 $r->id = $row['id'];
		 $r->name = $row['name'];
		 $r->price = $row['price'];
		 $r->des = $row['description'];
		 $r->img = $row['img'];
		 $r->qnty = $row['qnty'];
		 $arr[$i] = $r;
		 $i = $i + 1;
	  }
	  echo json_encode($arr);
	
?>