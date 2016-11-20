		<?php
			$type = "none"; //Default, this will be used when no post request is received.
			$orderStatus = "410"; //ErrorsPlacingOrderAtStore.
			$orderId = "9ccf5677-205a-4fb6-8262-c57b398936c0"; //Default from API documentation.
			$additionalInfo = "";
			
			$currentTime = date("Y-m-d H:i:s");
			$multiplier = 5;
			$vendorId;
			$orderTime;
			
		/**
		* Receive JSON POST request and convert it.
		**/
		$json = file_get_contents('php://input');
		if($json != null){
			$json = json_decode($json, true);
			if($json != null){
				$type = "DummyData"; //Some POST request detected!
				$vendorId = $json['VendorId'];
				$tempVendor = getOrderInfo($json['OrderId'])[1];
				//if($tempVendor == $vendorId){
					$orderTime = getOrderInfo($json['OrderId'])[2];
					$processedTime = strtotime($currentTime) - strtotime($orderTime);
					if($processedTime < 0.5*$multiplier){
						$orderStatus = "400";
					}else if($processedTime >= 0.5*$multiplier && $processedTime <= 1*$multiplier){
						$orderStatus = "800";
					}else if($processedTime >= 1*$multiplier && $processedTime <= 2.5*$multiplier){
						$orderStatus = "810";
					}else if($processedTime >= 2.5*$multiplier && $processedTime <= 8.5*$multiplier){
						$orderStatus = "820";
					}else if($processedTime >= 8.5*$multiplier && $processedTime <= 9.5*$multiplier){
						$orderStatus = "830";
					}else if($processedTime >= 9.5*$multiplier && $processedTime <= 18.5*$multiplier){
						$orderStatus = "850";
					}else if($processedTime > 18.5*$multiplier){
						$orderStatus = "867";
					}
					//$additionalInfo = $processedTime;
					//Reset order
					if($processedTime > 20*$multiplier){
						//2001-03-10 17:16:18   VS   2016-11-19 03:49:00
						$A = substr("$orderId##$vendorId##$orderTime", 0, -2);
						$B = "$orderId##$vendorId##$currentTime";
						//$additionalInfo = $A;//"Resetting order";
						updateConfig($A,$B );
					}
				//}else{
				//	$tempVendor = getOrderInfo($json['OrderId'])[2];
				//	$additionalInfo = "vendorId ($vendorId) or orderId ($orderId) is incorrect --> $tempVendor <--";	
				//}
			}else{
				$additionalInfo = "no json detected";
			}
		}
		
		
		?>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Dummino's API</title>
		<link rel="stylesheet" type="text/css" href="default.css">
	</head>
	<body>
		<?php
			if($type == "none"){
				?><b>This API is meant for testing the Domino's Android wear tracker.</b><br>
				Please send a POST request like the example below:<br>
				{<br>
					&nbsp;&nbsp;&nbsp;&nbsp;"CountryCode": "NL",<br>
					&nbsp;&nbsp;&nbsp;&nbsp;"VendorId": "36040d87-684e-4aee-9e03-3f4e17010b26",<br>
					&nbsp;&nbsp;&nbsp;&nbsp;"OrderId": "9ccf5677-205a-4fb6-8262-c57b398936c0"<br>
				}
				<?php
			}else if($type == "DummyData"){
				//This will return a "hardcoded" json with information based on some orders in a txt file.
				?>
					{
						"OrderStatus": "<?php echo $orderStatus ?>",
						"OrderId": "<?php echo $orderId ?>",
						"AdditionalInfo": "<?php echo $additionalInfo ?>"
					}
				<?php	
			}
		?>
		
	</body>
</html>



<?php
    /**
     * Adjust this string to the path of your configuration file.
     * The default path points to a config file in the same folder as the framework.
     *
     * @param $path (a string with the path to the config.txt file)
     * @return path to the configuration file
     *
     */
    function getConfigFile($path=NULL)
    {
        if(!isset($path))
            $path=dirname(__FILE__)."/";
        return $path."config.txt";
    }
	
	 /**
     * function that checks .txt files for a specific string and returns entire line without that string.
     *
     * @param $file (.txt file)
     * @param $search (string to search for in this file)
     * @return resulting line
     *
     */
    function searchInFile($file, $search)
    {
        $content = (file($file));
        for ($i = 0; $i < count($content); $i++) {
            if (strpos($content [$i], $search) !== false) {
                $result = $content [$i];
                return $result;
            }
        }
					echo "ERROR"; die;
        return "Text not found";
    }

    /**
     * gets value from config file
     *
     * @param value (function looks for the value of this parameter)
     * @return array containing OrderId, VendorId, OrderTimer.
     *
     */
    function getOrderInfo($value)
    {
        $var = searchInFile(getConfigFile(), "$value");
        if($var == 'Textnotfound'){
			$additionalInfo = "Invalid search";
            return false;
		}
        return explode('##',$var);
    }
	
	/**
     * This is a self made JSON interpreter
     *
     * @param str (the JSON as a string)
     * @return array containing OrderId, VendorId, OrderTimer.
     *
     */
	function readJsonFromString($str){
		$str = str_replace("{","",$str);
		$str = str_replace("}","",$str);
		$str = str_replace("\"","",$str);
		$array = explode(",", $str);
		$result = array();
		foreach($array as $tempValue){
			$keyValue = explode(":", $tempValue);
			$result[$keyValue[0]] = $keyValue[1];
		}
		return $result;
	}
	
	//$data = file('myfile'); // reads an array of lines
	function updateConfig($oldLine, $newLine) {
	   $data = file(getConfigFile());
	   for ($i = 0; $i < count($data); $i++) {
            if (strpos($data [$i], $oldLine) !== false) {
                $data[$i] = $newLine."\r\n";
				file_put_contents(getConfigFile(), implode('', $data));
				//fwrite()
				$additionalInfo = "Order Reset";
				return;
            }
        }
		$additionalInfo = "Failed to reset order";
	}
	

?>