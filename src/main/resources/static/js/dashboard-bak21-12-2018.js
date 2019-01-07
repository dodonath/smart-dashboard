/*Auto refresh time interval*/
var time_interval = $("#auto_refresh_time_interval").val();
var time_interval = time_interval * 60 * 1000;
/* base url to make ajax call */
var base_url = $("#base_url").val();
var totalSeconds = 0;
/*dynamic drop down logic from progression chart details*/
var selectedEntity = "";
var entitySelectionCheck = false;
var progressChartDetails = '[]';
var entityDetails = '[]';
var details = [];
function show() {
	// GET THE SELECTED VALUE FROM <select> ELEMENT AND SHOW IT.
	var e = document.getElementById("dropDownEntities");
	selectedEntity = e.options[e.selectedIndex].value;
	console.log('--->',e.options[e.selectedIndex].value);
	if (selectedEntity == "") {
		alert("kindly select proper entity name, 'Select Entity' is not proper entity ! ")
	} else {
		entitySelectionCheck = true;
		details = [];
		dataForVisiualization();
	}
}
function dataForVisiualization(){
	/* Progression chart */
	if(entitySelectionCheck){
		//progressChartDetails = JSON.parse('[  {    "migrationId": 1,    "migrationDate": "2018-12-12",    "clientCode": "Telecom",    "migrationDescription": "Migration Done at 12-12-2018",    "entities": [      {        "entityId": 1,        "entityType": "Accounts",        "progressionDetails": [          {            "environmentId": 1,            "environmentType": "csr",            "environmentCount": 2000          },          {            "environmentId": 2,            "environmentType": "rbm",            "environmentCount": 3000          },          {            "environmentId": 3,            "environmentType": "omnia",            "environmentCount": 2908          }        ]      },      {        "entityId": 2,        "entityType": "Customer",        "progressionDetails": [          {            "environmentId": 1,            "environmentType": "csr",            "environmentCount": 2801          },          {            "environmentId": 2,            "environmentType": "rbm",            "environmentCount": 2801          },          {            "environmentId": 3,            "environmentType": "omnia",            "environmentCount": 2801          }        ]      }    ]  },  {    "migrationId": 2,    "migrationDate": "2018-12-05",    "clientCode": "Telecom",    "migrationDescription": "Migration Done at 12-12-2018",    "entities": [      {        "entityId": 1,        "entityType": "Accounts",        "progressionDetails": [          {            "environmentId": 1,            "environmentType": "csr",            "environmentCount": 2908          },          {            "environmentId": 2,            "environmentType": "rbm",            "environmentCount": 3000          },          {            "environmentId": 3,            "environmentType": "omnia",            "environmentCount": 2800          }        ]      },      {        "entityId": 2,        "entityType": "Customer",        "progressionDetails": [          {            "environmentId": 1,            "environmentType": "csr",            "environmentCount": 2801          },          {            "environmentId": 2,            "environmentType": "rbm",            "environmentCount": 2801          },          {            "environmentId": 3,            "environmentType": "omnia",            "environmentCount": 2801          }        ]      }    ]  },  {    "migrationId": 3,    "migrationDate": "2018-11-28",    "clientCode": "Telecom",    "migrationDescription": "Migration Done at 12-12-2018",    "entities": [      {        "entityId": 1,        "entityType": "Accounts",        "progressionDetails": [          {            "environmentId": 1,            "environmentType": "csr",            "environmentCount": 2800          },          {            "environmentId": 2,            "environmentType": "rbm",            "environmentCount": 2800          },          {            "environmentId": 3,            "environmentType": "omnia",            "environmentCount": 2801          }        ]      },      {        "entityId": 2,        "entityType": "Billing",        "progressionDetails": [          {            "environmentId": 1,            "environmentType": "csr",            "environmentCount": 2801          },          {            "environmentId": 2,            "environmentType": "rbm",            "environmentCount": 2801          },          {            "environmentId": 3,            "environmentType": "omnia",            "environmentCount": 2801          }        ]      }    ]  } ]');
		details.push([ 'Migration Date', 'Source', 'Talend', 'Target' ]);
		for (var idx = 0; idx < progressChartDetails.length; idx++) {
			var progressChartDetail = progressChartDetails[idx];
			/*
			 * console.log(progressChartDetail.migrationId);
			 * console.log(progressChartDetail.migrationDescription);
			 * console.log(progressChartDetail.entityId);
			 * console.log(progressChartDetail.entityType);
			 */
			//var progressDetails = progressChartDetail.progressionDetails;
			var subData = [];
			var migrationId = progressChartDetail.migrationId;
			var migrationDate = progressChartDetail.migrationDate;
			
			var entityDetails = progressChartDetail.entities;
			for(var en = 0; en<entityDetails.length; en++){
				var entityDetail = entityDetails[en];
				var entityFilter = entityDetail.entityType;
				if(entityFilter == selectedEntity){
					var progressDetails = entityDetail.progressionDetails;
					subData.push(migrationDate);
					for(var i=0;i<progressDetails.length;i++){
						/*
						console.log(progressDetails[i].environmentId);
						console.log(progressDetails[i].environmentType);
						console.log(progressDetails[i].environmentCount);
						*/
						subData.push(progressDetails[i].environmentCount);
					}
					details.push(subData);
					subData = [];
				}
			}
		}
		console.log('Details : ', details);
		google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(drawVisualization);
	}
}

function drawVisualization() {
  // Some raw data (not necessarily accurate)
  var data = google.visualization.arrayToDataTable(details);

  var options = {
    title : 'Migration Progression Stats',
    vAxis: {title: 'Count'},
    hAxis: {title: 'Migration Date'},
    seriesType: 'bars',
    series: {5: {type: 'line'}}
  };

  var chart = new google.visualization.ComboChart(document.getElementById('migration-progression-chart'));
  chart.draw(data, options);
}
/*Chart End*/

var getProgressionChartDetails = document.getElementById("get-progression-chart-details");
getProgressionChartDetails.addEventListener("click", function () {
	var getEntitiesRequest = new XMLHttpRequest();
	getEntitiesRequest.open('GET','http://localhost:8080/dashboard/fetchEntityData');
	getEntitiesRequest.onload = function() {
		if (getEntitiesRequest.status >= 200 && getEntitiesRequest.status < 400) {
			entityDetails = JSON.parse(getEntitiesRequest.responseText);
			var selectElement = document.getElementById('dropDownEntities');
			/*selectElement.innerHTML = selectElement.innerHTML + '<option value="">Select Entity</option>';*/
			for (var i = 0; i < entityDetails.length; i++) {
				selectElement.innerHTML = selectElement.innerHTML + '<option value="' + entityDetails[i]['entityType'] + '">' + entityDetails[i]['entityType'] + '</option>';
			}
			console.log(getEntitiesRequest.responseText);
			//renderHTML(entityDetails);
		} else {
			console.log("We connected to the server, but it returned an error.");
		}
	};
	getEntitiesRequest.onerror = function() {
	    console.log("Connection error");
	};
	getEntitiesRequest.send();
	$('#dropDownEntities').empty();
	
	var fetchProgressChartRequest = new XMLHttpRequest();
	fetchProgressChartRequest.open('GET','http://localhost:8080/dashboard/fetchProgressChart');
	fetchProgressChartRequest.onload = function() {
		if (fetchProgressChartRequest.status >= 200 && fetchProgressChartRequest.status < 400) {
			progressChartDetails = JSON.parse(fetchProgressChartRequest.responseText);
			console.log(fetchProgressChartRequest.responseText);
			//renderHTML(entityDetails);
		} else {
			console.log("We connected to the server, but it returned an error.");
		}
	};
	fetchProgressChartRequest.onerror = function() {
	    console.log("Connection error");
	};
	fetchProgressChartRequest.send();
	
});

/* function to pad 0 to the time value */
function pad(val) {
    var valString = val + "";
    if (valString.length < 2) {
        return "0" + valString;
    } else {
        return valString;
    }
}
$(document).ready(function () {
    /* Ajax Call on page load to update dashboard */
    $.ajax({
        type: "POST",
        dataType: 'json',
        data: {'entity_name': $("#entity_name").val()},
        url: base_url + "api.php",
        async: true,
        success: function (response) {
            /* Passing response to update page function to update dashbaord contents */
            update_page(response);
        }
    });
    $("#entity_name").on("change", function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            data: {'entity_name': $(this).val()},
            url: base_url + "api.php",
            async: true,
            success: function (response) {
                /* Passing response to update page function to update dashbaord contents */
                update_page(response);
            }
        });
    });
    /* Ajax call to auto refresh dashboard contents */
    setInterval(function () {
        $.ajax({
            type: "POST", //rest Type
            dataType: 'json',
            data: {'entity_name': $("#entity_name").val()},
            url: base_url + "api.php",
            async: false,
            success: function (response) {
                update_page(response);
            }
        }
        );
    }, time_interval);
    /* time_interval in milliseconds */

    /* script to start and stop the migration*/
    $("#start_button_top").on('click', function () {
    	/*$output = shell_exec('./logFolders.sh');*/
        if ($(this).hasClass('start')) {
            $.ajax({
                type: "POST",
                dataType: 'json',
                data: {entity_name: 'All', action: 'Start'},
                url: base_url + "start-stop-migration.php",
                async: true,
                success: function (response) {
                    if (!response['status']) {
                        alert(response['message']);
                    } else {
                        $("#start_button_top").removeClass('start').addClass('started');
                        $("#stop_button_top").removeClass('stop').addClass('stopped');
                    }
                }
            });

        }
    });

    $("#stop_button_top").on('click', function () {
        if ($(this).hasClass('stopped')) {
            $.ajax({
                type: "POST",
                dataType: 'json',
                data: {entity_name: 'All', action: 'Stop'},
                url: base_url + "start-stop-migration.php",
                async: true,
                success: function (response) {
                    if (!response['status']) {
                        alert(response['message']);
                    } else {
                        $("#start_button_top").removeClass('started').addClass('start');
                        $("#stop_button_top").removeClass('stopped').addClass('stop');
                    }
                }
            });


        }
    });

    /* script to start and stop the migration for individual entity*/
    $("#start_button_entity").on('click', function () {
        if ($(this).hasClass('start')) {
            $.ajax({
                type: "POST",
                dataType: 'json',
                data: {entity_name: $("#entity_name").val(), action: 'Start'},
                url: base_url + "start-stop-migration.php",
                async: true,
                success: function (response) {
                    if (!response['status']) {
                        alert(response['message']);
                    } else {
                        $("#start_button_entity").removeClass('start').addClass('started');
                        $("#stop_button_entity").removeClass('stop').addClass('stopped');
                    }
                }
            });

        }
    });

    $("#stop_button_entity").on('click', function () {
        if ($(this).hasClass('stopped')) {
            $.ajax({
                type: "POST",
                //dataType: 'json',
                data: {entity_name: $("#entity_name").val(), action: 'Stop'},
                url: base_url + "start-stop-migration.php",
                async: true,
                success: function (response) {
                    if (!response['status']) {
                        alert(response['message']);
                    } else {
                        $("#start_button_entity").removeClass('started').addClass('start');
                        $("#stop_button_entity").removeClass('stopped').addClass('stop');
                    }
                }
            });


        }
    });

    $("#start_reconciliation").on('click', function () {
        if ($(this).hasClass('start')) {
            $.ajax({
                type: "POST",
                //dataType: 'json',
                data: {entity_name: $("#entity_name").val(), action: 'Stop'},
                url: base_url + "start-reconciliation.php",
                async: true,
                success: function (response) {
                    // alert(response);
                }
            });
            $("#start_reconciliation").removeClass('start').addClass('started');
        }
    });

    /* Function to update dashboard html*/
    function update_page(response) {
        if (response.hide_top_button == '1') {
            $("#start_button_top").addClass('hide');
            $("#stop_button_top").addClass('hide');
        } else {
            $("#start_button_top").removeClass('hide');
            $("#stop_button_top").removeClass('hide')
        }
        if (response.hide_entity_button == '1') {
            $("#start_button_entity").addClass('hide');
            $("#stop_button_entity").addClass('hide');
        } else {
            $("#start_button_entity").removeClass('hide');
            $("#stop_button_entity").removeClass('hide')
        }
        var completed_row_html = '';
        /*in progress job is exist in progress file then update the progress tab */
        if (response.total_inprogress_jobs == 1)
        {
            $("#current_running_job").css('display', "block");
            $("#current_running_job_title").text(response.inprogress_job['Job']);
            $("#current_running_job_progress").attr('aria-valuenow', response.inprogress_job['Completion']);
            $("#current_running_job_progress").css('width', response.inprogress_job['Completion'] + "%");
            $("#current_running_job_progress_text").text("Processing: " + response.inprogress_job['Completion'] + "%");

            /*If job status is not complete or in progress or not started then it will consider as error */
            if (response.inprogress_job["Status"] == 'Complete' || response.inprogress_job["Status"] == 'In Progress' || response.inprogress_job["Status"] == 'Not Started' || response.inprogress_job["Status"] == 'Pending')
            {
                $(".progress-bar-info").removeClass('red-pregress')
            } else {
                $(".progress-bar-info").addClass('red-pregress')
            }

        } else {
            /* If progress job not exist then it hides the probress tab */
            $("#current_running_job").css('display', "none");
        }
        /* Setting overall progress tab contents */
        $("#overall_progress").attr('aria-valuenow', response['overall_percentage_complete']);
        $("#overall_progress").css('width', response['overall_percentage_complete'] + "%");
        $("#overall_progress_text").text("Processing: " + response['overall_percentage_complete'] + "%");

        /* if 100% job done then showing success message. */
        if (response['overall_percentage_complete'] == 100) {

            $("#error_message").css("display", "none");
            $("#success_message").text(response['success']);
            $("#success_message").css("display", "block");
            $("#ReconciliationCount").css("display", "block");
        }
        /* if error is not empty then showing error */
        if (response['error'] != '') {
            $("#error_message").css("display", "block");
            $("#error_message").text(response['error']);
            $("#success_message").css("display", "none");
        } else if (response['error'] == '') {
            /*Other wise hidding error element*/
            $("#error_message").css("display", "none");
            $("#error_message").text('');
        }
        /* Checking if migration is started or not  */
        if (response['migration_started'] == 1) {
            /* if migration started then only updating elapsed time */
            var elapsed_time = response['elapsed_time'];
            var elapsed_time_arr = elapsed_time.split(":");
            $("#elapsed_hours").text(elapsed_time_arr[0]);
            $("#elapsed_minutes").text(elapsed_time_arr[1]);
            $("#elapsed_seconds").text(elapsed_time_arr[2]);

            var totalSeconds = response['total_elapsed_seconds'];
            $("#elapsed_time").attr('data-totalseconds', totalSeconds);
            if (typeof elapsed_time_interval === 'undefined') {
                // variable is undefined
            } else {
                clearInterval(elapsed_time_interval); // stop the interval
            }
            /* Clock counter for elapsed time */
            if (response['overall_percentage_complete'] != 100) {
                elapsed_time_interval = setInterval(function () {
                    ++totalSeconds;
                    $("#elapsed_seconds").html(pad(totalSeconds % 60));
                    $("#elapsed_minutes").html(pad(parseInt(totalSeconds / 60)));
                    $("#elapsed_hours").html(pad(parseInt((parseInt(totalSeconds / 60)) / 60)));
                }, 1000);
            }
        }

        var remaining_time = response['remaining_time'];
        var remaining_time_arr = remaining_time.split(":");
        $("#remaining_hours").text(remaining_time_arr[0]);
        $("#remaining_minutes").text(remaining_time_arr[1]);
        $("#remaining_seconds").text(remaining_time_arr[2]);

        var totalremainingSeconds = response['remaining_time_seconds'];
        $("#remaining_time").attr('data-totalremainingseconds', totalremainingSeconds);
        if (typeof remaining_time_interval === 'undefined') {
            // variable is undefined
        } else {
            clearInterval(remaining_time_interval); // stop the interval
        }
        /* Clock counter for remaining time */
        remaining_time_interval = setInterval(function () {
            totalremainingSeconds--;
            /* if clock counter goes to zero and it is last job then updateing remaining time to 10 */
            if (response['last_job'] == 1 && response['total_inprogress_jobs'] == 1 && totalremainingSeconds == 0) {
                totalremainingSeconds = 10;
            }
            if (totalremainingSeconds >= 0) {
                $("#remaining_seconds").html(pad(totalremainingSeconds % 60));
                $("#remaining_minutes").html(pad(parseInt((totalremainingSeconds / 60) % 60)));
                $("#remaining_hours").html(pad(parseInt((parseInt(totalremainingSeconds / 60)) / 60)));
            }
        }, 1000);

        /* updating job status tab according to job status 
         
         $.each(response.job_list, function (key, job) {
         if (job["Status"] == 'Complete' || job["Status"] == 'In Progress' || job["Status"] == 'Not Started' || job["Status"] == 'Pending')
         {
         error_class = 'center';
         } else {
         error_class = 'text-red center';
         }
         
         if ($("#class_name").val() === '0') {
         class_str = 'test1';
         } else {
         class_str = 'test2';
         }
         completed_row_html += '<tr align="left" valign="top" class="' + class_str + '">';
         completed_row_html += '<td>' + job["Job"] + '</td>';
         completed_row_html += '<td class=' + error_class + '>' + job["Status"] + '</td>';
         completed_row_html += '<td class="text-center">' + job["ActualRunTime"] + '</td>';
         completed_row_html += '<td class="text-center">' + job["Source Count"] + '</td>';
         completed_row_html += '<td class="text-center">' + job["Target Count"] + '</td>';
         completed_row_html += '</tr>';
         });
         // Appending the new status html and removing the old status 
         $("#jobs_list tbody").append(completed_row_html);
         */
        if ($("#class_name").val() === '0') {
            $("#jobs_list tbody tr.test2").remove();
            $("#class_name").val(1);
        } else {
            $("#class_name").val(0);
            $("#jobs_list tbody tr.test1").remove();
        }
        configure_buttons(response.entity_status);
        draw_meter(response.meter_data);
        draw_bar_chart(response.bar_chart_data);
        draw_entity_load_status_chart(response.entity_load_staus_data);
        draw_reconciliation(response.reconciliation);
    }

    function configure_buttons(entity_status) {
//        console.log(entity_status);
        $.each(entity_status, function (key, status_data) {
            if (status_data['Entity'] == 'All') {
                if (status_data['Status'] == '1') {
                    $("#start_button_top").removeClass('start').addClass('started');
                    $("#stop_button_top").removeClass('stop').addClass('stopped');
                } else {
                    $("#start_button_top").removeClass('started').addClass('start');
                    $("#stop_button_top").removeClass('stopped').addClass('stop');
                }
            } else {
                if (status_data['Entity'] == $("#entity_name").val()) {
                    if (status_data['Status'] == '1') {
                        $("#start_button_entity").removeClass('start').addClass('started');
                        $("#stop_button_entity").removeClass('stop').addClass('stopped');
                    } else {
                        $("#start_button_entity").removeClass('started').addClass('start');
                        $("#stop_button_entity").removeClass('stopped').addClass('stop');
                    }
                }
            }
        });
    }
    /* Function to drow meter for selected entity */

    function draw_meter(meter_data) {

        /* Custom Gauge */
        $('#collected-title').html(meter_data.collected.title);
        $('#validated-title').html(meter_data.validated.title);
        $('#transformed-title').html(meter_data.transformed.title);
        $('#loaded-title').html(meter_data.loaded.title);
        var gauge = new Gauge(document.getElementById("collected"));
        gauge.value(meter_data.collected.value);
        var gauge = new Gauge(document.getElementById("validated"));
        gauge.value(meter_data.validated.value);
        var gauge = new Gauge(document.getElementById("transformed"));
        gauge.value(meter_data.transformed.value);
        var gauge = new Gauge(document.getElementById("loaded"));
        //console.log("gauge--",gauge);
        gauge.value(0.5);
        console.log("--->",meter_data.loaded.title);
        //console.log("--->",meter_data.loaded.value);
        //console.log("--->",gauge.value(meter_data.loaded.value));
    }

    /* Function to bar chart for selected entity */
    function draw_bar_chart(bar_chart_data) {
        $("#ct-chart").html('');

        if (bar_chart_data.error_message != '') {
            $("#ct-chart").html('<div class="bar-chart-error">' + bar_chart_data.error_message + '</div>');
        } else {

            var chart1 = new Chartist.Bar('#ct-chart', bar_chart_data.data, {
                low: 0,
                high: bar_chart_data.high,
                height: 120,
                width: bar_chart_data.width,
                axisY: {
                    onlyInteger: true,
                    scaleMinSpace: 8
                },
                plugins: [
                    Chartist.plugins.ctBarLabels({
                        position: {
                            x: function (data) {
                                return data.x1
                            },
                            y: function (data) {
                                return data.y2 - 10
                            }
                        }
                    }),
                    Chartist.plugins.ctAxisTitle({
                        axisY: {
                            axisTitle: 'ERRORS',
                            axisClass: 'ct-axis-title-test2',
                            offset: {
                                x: -40,
                                y: -40
                            },
                            flipTitle: false
                        }
                    })
                ]
            }).on('draw', function (data) {
                 $('#ct-chart .ct-chart-bar').css("height", "105px");
                if (data.type === 'bar') {
                    data.element.attr({
                        style: 'stroke-width:35px'
                    });
                }
            });
            var addedEvents = false;
            chart1.on('created', function (data) {
                
                if (!addedEvents) {
                    /*Adding the class*/
                    addedEvents = true;
                    $('#ct-chart').on('click', 'line.ct-bar', function (evt) {
                        var index = (($(this).index()) / 2) + 1;
                        var label = $(this).closest('.ct-chart-bar').find('.ct-labels foreignObject:nth-child(' + (index) + ') span').text();
                        var dataURL = "error-list.php?error_code=" + label + "&error_file_name=" + bar_chart_data.error_file[label];
                        $('.modal-body').load(dataURL, function () {
                            $('#myModal').modal({show: true});
                        });

                    });

                    $('.ct-bar').on('click', function () {

                        //                    var rowClass = $(this).attr('ct:meta');
                        //                    var rowClass = rowClass.replace(/ /g, "-");
                        //                    $('.' + rowClass).addClass("row-highlight");
                    });
                }
            });
            //   $("#ct-chart").prepend('<div class="x-lable">Errors</div>');
        }

    }

    /* Function to draw entity load status */
    function draw_entity_load_status_chart(entity_load_status_data) {
        if (entity_load_status_data.message != '') {
            message_html = '<div class="entity_error_message">' + entity_load_status_data.message + '</div>';
            $(".ct-chart-2").html(message_html);
            $("#legent_entity_load_status").css("display", "none");
        } else {
            $(".ct-chart-2").html('');
            $("#legent_entity_load_status").css("display", "block");
            var chart2 = new Chartist.Bar('.ct-chart-2', entity_load_status_data.data, {
                chartPadding: {
                    right: 50,
                    bottom: 0,
                    top: 0
                },
                height: 280,
                stackBars: true,
                horizontalBars: true,
                reverseData: false,
                axisX: {
                    labelInterpolationFnc: function (value) {
                        return value;
                    },
                    onlyInteger: true,
                    showLabel: false,
                    scaleMinSpace: 40
                },
                axisY: {
                    offset: 135,
                },
                plugins: [
                    Chartist.plugins.ctBarLabels({
                        position: {
                            x: function (data) {
                                return (data.x1 + data.x2) / 2
                            }
                        },
                        labelOffset: {
                            y: 4
                        },
                        labelInterpolationFnc: function (text) {
                            return text
                        }
                    })
                ]
            });
            chart2.on('draw', function (data) {
                if (data.type === 'bar') {
                    data.element.attr({
                        style: 'stroke-width:20px'
                    });
                }
            });
        }
    }

    /* Function to draw reconciliation slider */
    function draw_reconciliation(reconciliation) {
        if (reconciliation.error_message != '') {
            $("#ReconciliationCountData").html('<div class="bar-chart-error">' + reconciliation.error_message + '</div>');
            $("#ReconciliationCountButton").css("display", "block");
        } else {
            var reconciliation_inner_html = '';
            var reconciliation_html = '';
            var item_html = '';
            var item_inner_html = '';
            var last_key;
            var first_item = 1;
            var item_class = '';
            $.each(reconciliation.data, function (key, data) {
                $("#ReconciliationCountButton").css("display", "none");
                item_inner_html += '<a href="javascript:void(0);" class="reconciliation-item" data-file-name="' + data['File Name'] + '" data-name="' + data['Name'] + '">';
                item_inner_html += '<div class="col"><span class="num">' + data['Reconciliation Count'] + '</span><hr><p>' + data['Name'] + '</p></div>';
                item_inner_html += '</a>';
                if ((key + 1) % 2 == 0) {
                    item_html += '<div class="slide-col">' + item_inner_html + '</div>';
                    item_inner_html = '';
                }
                if ((key + 1) % 16 == 0) {
                    if (first_item) {
                        first_item = 0;
                        item_class = 'active';
                    } else {
                        item_class = '';
                    }
                    reconciliation_inner_html += '<div class="carousel-item ' + item_class + '">' + item_html + '</div>';
                    item_html = '';
                }
                last_key = key;
            });
            if (item_inner_html != '') {
                item_html += '<div class="slide-col">' + item_inner_html + '</div>';
            }

            if ((last_key + 1) % 16 != 0)
            {
                if (last_key < 15)
                {
                    reconciliation_inner_html += '<div class="carousel-item active">' + item_html + '</div>';
                } else {
                    reconciliation_inner_html += '<div class="carousel-item">' + item_html + '</div>';

                }
            }


            reconciliation_html += '<div id="carousel" class="carousel slide" data-ride="carousel"><div class="carousel-inner no-padding" id="carousel-div">' + reconciliation_inner_html + '</div>';
            if (last_key > 15)
            {
                reconciliation_html += '<a class="carousel-control-prev" href="#carousel" data-slide="prev"><span class="carousel-control-prev-icon"></span></a>';
                reconciliation_html += '<a class="carousel-control-next" href="#carousel" data-slide="next"><span class="carousel-control-next-icon"></span></a>';
            }
            reconciliation_html += '</div>';
            $("#ReconciliationCountData").html(reconciliation_html);
            $('#carousel').carousel({
                interval: false
            });

            $(".reconciliation-item").on("click", function () {
                $('#myModal').modal({
                    show: true
                });
                var file_name = $(this).attr('data-file-name');
                var name = $(this).attr('data-name');
                var reconciliation_data_URL = "reconciliation-detail-list.php?file_name=" + file_name + "&name=" + encodeURI(name);
                $('.modal-body').load(reconciliation_data_URL, function () {

                });
            });
        }

    }
});