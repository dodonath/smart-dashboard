/*dynamic drop down logic from progression chart details*/
var host = "http://localhost:8080/";
var selectedEntity = "";
var selectedCurMigEntity = "";
var selectedLoadedEntity = "";
var entitySelectionCheck = false;
var progressChartDetails = '[]';
var allMigEntityDetails = [];
var curMigEntityDetails = [];
var currentValidationData = [];
var currentLoadedData = [];
//currentLoadedData = JSON.parse('[   {     "migrationId": 5,     "migrationDate": "2019-01-03",     "clientCode": "Telecom",     "migrationDescription": "Migration on date 2019-01-03T05:52:54.924Z",     "entities": [       {         "entityId": 2,         "entityType": "Customer",         "migrationStats": {           "collected": 1357,           "loaded": 1200         },         "errors": [           {             "errorCode": "EE0004",             "errorId": 4,             "errorCount": 3           },           {             "errorCode": "EE0006",             "errorId": 6,             "errorCount": 2           },           {             "errorCode": "EE0007",             "errorId": 7,             "errorCount": 2           }         ]       },       {         "entityId": 1,         "entityType": "Accounts",         "migrationStats": {           "collected": 663,           "loaded": 20         },         "errors": [           {             "errorCode": "EE0001",             "errorId": 1,             "errorCount": 2           },           {             "errorCode": "EE00021",             "errorId": 2,             "errorCount": 2           },           {             "errorCode": "EE00031",             "errorId": 3,             "errorCount": 1           },           {             "errorCode": "EE00051",             "errorId": 5,             "errorCount": 2           }         ]       }     ]   } ]');
var barData = {};
var details = [];
var onLoad = false;
var CSV = '';

function showValidationMeter() {
    // GET THE SELECTED VALUE FROM <select> ELEMENT AND SHOW IT.
    var e = document.getElementById("selectValidationEntities");
    selectedCurMigEntity = e.options[e.selectedIndex].value;
    if (selectedCurMigEntity == "") {
        alert("kindly select proper entity name, 'Select Entity' is not proper entity ! ")
    } else {
        drawValidationGaugeMeter();
    }
}

function showLoadedMeter() {
    // GET THE SELECTED VALUE FROM <select> ELEMENT AND SHOW IT.
    var e = document.getElementById("selectLoadedEntities");
    selectedLoadedEntity = e.options[e.selectedIndex].value;
    if (selectedLoadedEntity == "") {
        alert("kindly select proper entity name, 'Select Entity' is not proper entity ! ")
    } else {
        drawLoadedGaugeMeter();
    }
}

function showProgressionChart() {
    // GET THE SELECTED VALUE FROM <select> ELEMENT AND SHOW IT.
    var e = document.getElementById("selectProgressionChartEntities");
    selectedEntity = e.options[e.selectedIndex].value;
    if (selectedEntity == "") {
        alert("kindly select proper entity name, 'Select Entity' is not proper entity ! ")
    } else {
        entitySelectionCheck = true;
        details = [];
        dataVisiualizationForProgressionChart();
    }
}

function dataVisiualizationForProgressionChart() {
    /* Progression chart */
    if (entitySelectionCheck) {

        details.push(progressChartDetails.progressChartsHeader);

        var progressChartsValues = progressChartDetails.progressChartsValues;

        for (var idx = 0; idx < progressChartsValues.length; idx++) {
            var progressChartDetail = progressChartsValues[idx];
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
            for (var en = 0; en < entityDetails.length; en++) {
                var entityDetail = entityDetails[en];
                var entityFilter = entityDetail.entityType;
                if (entityFilter == selectedEntity) {
                    var progressDetails = entityDetail.progressionDetails;
                    subData.push(migrationDate);
                    for (var i = 0; i < progressDetails.length; i++) {
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
        google.charts.load('current', {
            'packages': ['corechart']
        });
        google.charts.setOnLoadCallback(drawVisualization);
    }
}

function drawVisualization() {
    var data = google.visualization.arrayToDataTable(details);
    var options = {
        title: 'Migration Progression Stats',
        vAxis: {
            title: 'Count'
        },
        hAxis: {
            title: 'Migration Date'
        },
        seriesType: 'bars',
        series: {
            5: {
                type: 'line'
            }
        }
    };
    var chart = new google.visualization.ComboChart(document.getElementById('migration-progression-chart'));
    chart.draw(data, options);
    /*PDF Logic after draw Chart need to import jspdf.min.js
    https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.2/jspdf.min.js*/
    var doc = new jsPDF("p", "mm", "a4");
    var width = doc.internal.pageSize.getWidth();
    var height = doc.internal.pageSize.getHeight();
    var btnSave = document.getElementById('save-pdf');
    google.visualization.events.addListener(chart, 'ready', function() {
        btnSave.disabled = false;
    });

    btnSave.addEventListener('click', function() {
        doc.addImage(chart.getImageURI(), 'JPEG', 0, 0, width, height / 5); //doc.addImage(chart.getImageURI(), 0, 0);//
        doc.save('chart.pdf');
    }, false);
}

function drawValidationGaugeMeter() {
    var migrationId = currentValidationData[0].migrationId;
    for (var idx = 0; idx < currentValidationData[0].entities.length; idx++) {
        var curEntity = currentValidationData[0].entities[idx].entityType;
        if (curEntity == selectedCurMigEntity) {
            var entityId = currentValidationData[0].entities[idx].entityId;
            var collected = currentValidationData[0].entities[idx].migrationStats.collected;
            var validated = currentValidationData[0].entities[idx].migrationStats.validated;
            var transformed = currentValidationData[0].entities[idx].migrationStats.transformed;
            $('#collected-title').html('<span>' + collected + '</span>/' + collected + ' <label>High</label>');
            $('#validated-title').html('<span>' + validated + '</span>/' + collected + ' <label>High</label>');
            $('#transformed-title').html('<span>' + transformed + '</span>/' + collected + ' <label>High</label>');
            var gauge = new Gauge(document.getElementById("collected"));
            gauge.value(collected / collected);
            var gauge = new Gauge(document.getElementById("validated"));
            gauge.value(validated / collected);
            /*--Adding percentage for progression bar--*/
            var barPercentage = barPercentage = (validated / collected) * 100;
            var barElement = document.getElementById('progressValidationBarStats');
            barElement.innerHTML = "";
            barElement.innerHTML = barElement.innerHTML + '<div id="overall_progress" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="35" aria-valuemin="0" aria-valuemax="100" style="width: ' + Math.round(barPercentage) + '%"> <span id="overall_progress_text">Processed: ' + Math.round(barPercentage) + '%</span> </div>';
            //--
            var gauge = new Gauge(document.getElementById("transformed"));
            gauge.value(transformed / collected);
            //Bar chart creation
            var errorDetails = currentValidationData[0].entities[idx].errors;
            var barLabel = [];
            var barSeries = [];
            var finalSeries = [];
            for (var edx = 0; edx < errorDetails.length; edx++) {
                barLabel.push(errorDetails[edx].errorCode);
                barSeries.push(errorDetails[edx].errorCount);
            }
            finalSeries.push(barSeries);
            bardata = {
                labels: barLabel,
                series: finalSeries
            };
            $("#ct-chart").html('');
            var chart1 = new Chartist.Bar('#ct-chart', bardata, {
                low: 0,
                height: 120,
                axisY: {
                    onlyInteger: true,
                    scaleMinSpace: 8
                },
                plugins: [
                    Chartist.plugins.ctBarLabels({
                        position: {
                            x: function(bardata) {
                                return bardata.barLabel
                            },
                            y: function(bardata) {
                                return bardata.barSeries - 10
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
            }).on('draw', function(data) {
                $('#ct-chart .ct-chart-bar').css("height", "105px");
                if (data.type === 'bar') {
                    data.element.attr({
                        style: 'stroke-width:35px'
                    });
                }
            });
            var addedEvents = false;
            chart1.on('created', function(data) {

                if (!addedEvents) {
                    /*Adding the class*/
                    addedEvents = true;
                    $('#ct-chart').on('click', 'line.ct-bar', function(evt) {
                        var index = (($(this).index()) / 2) + 1;
                        var label = $(this).closest('.ct-chart-bar').find('.ct-labels foreignObject:nth-child(' + (index) + ') span').text();
                        var jsonReqData = '{   "entityId": ' + entityId + ',   "errorCode": "' + label + '",   "migrationId": ' + migrationId + '  }';
                        $.ajax({
                            type: 'POST',
                            url: host+'dashboard/fetchErrorData',
                            data: jsonReqData,
                            contentType: "application/json",
                            success: function(ResData) {
                                transformToTable(ResData, "", true);
                                $('.modal-body').load(CSV, function() {
                                    $('#myModal').modal({
                                        show: true
                                    });
                                    var selectElement1 = document.getElementById('errorDetails');
                                    selectElement1.innerHTML = CSV;
                                });
                            }
                        });
                        /*var dataURL = "error-list.php?error_code=" + label + "&error_file_name=" + bar_chart_data.error_file[label];
                        console.log("dataURL--->",dataURL);
                        $('.modal-body').load(dataURL, function () {
                            $('#myModal').modal({show: true});
                        });*/

                    });

                    $('.ct-bar').on('click', function() {

                        //                    var rowClass = $(this).attr('ct:meta');
                        //                    var rowClass = rowClass.replace(/ /g, "-");
                        //                    $('.' + rowClass).addClass("row-highlight");
                    });
                }
            });
        }
    }
}

function drawLoadedGaugeMeter() {
    var migrationId = currentLoadedData[0].migrationId;
    for (var idx = 0; idx < currentLoadedData[0].entities.length; idx++) {
        var curEntity = currentLoadedData[0].entities[idx].entityType;
        if (curEntity == selectedLoadedEntity) {
            var entityId = currentLoadedData[0].entities[idx].entityId;
            var collected = currentLoadedData[0].entities[idx].migrationStats.collected;
            var loaded = currentLoadedData[0].entities[idx].migrationStats.loaded;
            $('#loaded-title').html('<span>' + loaded + '</span>/' + collected + ' <label>High</label>');
            /*--Adding percentage for progression bar--*/
            var barPercentage = barPercentage = (loaded / collected) * 100;
            var barElement = document.getElementById('progressLoadedBarStats');
            barElement.innerHTML = "";
            barElement.innerHTML = barElement.innerHTML + '<div id="overall_progress" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="35" aria-valuemin="0" aria-valuemax="100" style="width: ' + Math.round(barPercentage) + '%"> <span id="overall_progress_text">Processed: ' + Math.round(barPercentage) + '%</span> </div>';
            //--
            var gauge = new Gauge(document.getElementById("loaded"));
            gauge.value(loaded / collected);
            //Bar chart creation
            var errorDetails = currentLoadedData[0].entities[idx].errors;
            var barLabel = [];
            var barSeries = [];
            var finalSeries = [];
            for (var edx = 0; edx < errorDetails.length; edx++) {
                barLabel.push(errorDetails[edx].errorCode);
                barSeries.push(errorDetails[edx].errorCount);
            }
            finalSeries.push(barSeries);
            bardata = {
                labels: barLabel,
                series: finalSeries
            };
            $("#ct-chart-loaded").html('');
            var chart1 = new Chartist.Bar('#ct-chart-loaded', bardata, {
                low: 0,
                height: 120,
                axisY: {
                    onlyInteger: true,
                    scaleMinSpace: 8
                },
                plugins: [
                    Chartist.plugins.ctBarLabels({
                        position: {
                            x: function(bardata) {
                                return bardata.barLabel
                            },
                            y: function(bardata) {
                                return bardata.barSeries - 10
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
            }).on('draw', function(data) {
                $('#ct-chart-loaded .ct-chart-bar').css("height", "105px");
                if (data.type === 'bar') {
                    data.element.attr({
                        style: 'stroke-width:35px'
                    });
                }
            });
            var addedEvents = false;
            chart1.on('created', function(data) {

                if (!addedEvents) {
                    /*Adding the class*/
                    addedEvents = true;
                    $('#ct-chart-loaded').on('click', 'line.ct-bar', function(evt) {
                        var index = (($(this).index()) / 2) + 1;
                        var label = $(this).closest('.ct-chart-bar').find('.ct-labels foreignObject:nth-child(' + (index) + ') span').text();
                        var jsonReqData = '{   "entityId": ' + entityId + ',   "errorCode": "' + label + '",   "migrationId": ' + migrationId + '  }';
                        $.ajax({
                            type: 'POST',
                            url: host+'dashboard/fetchErrorData',
                            data: jsonReqData,
                            contentType: "application/json",
                            success: function(jsonResData) {
                                transformToTable(jsonResData, "", true);
                                $('.modal-body').load(CSV, function() {
                                    $('#myModal').modal({
                                        show: true
                                    });
                                    var selectElement1 = document.getElementById('errorDetails');
                                    selectElement1.innerHTML = CSV;
                                });
                            }
                        });
                        /*var dataURL = "error-list.php?error_code=" + label + "&error_file_name=" + bar_chart_data.error_file[label];
                        console.log("dataURL--->",dataURL);
                        $('.modal-body').load(dataURL, function () {
                            $('#myModal').modal({show: true});
                        });*/

                    });

                    $('.ct-bar').on('click', function() {

                        //                    var rowClass = $(this).attr('ct:meta');
                        //                    var rowClass = rowClass.replace(/ /g, "-");
                        //                    $('.' + rowClass).addClass("row-highlight");
                    });
                }
            });
        }
    }
}

function transformToTable(jsonResData, ReportTitle, ShowLabel) {
    //If jsonResData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof jsonResData != 'object' ? JSON.parse(jsonResData) : jsonResData;
    //Set Report title in first row or line
    //CSV += ReportTitle + '\r\n\n';
    //This condition will generate the Label/Header
    CSV = '';
    if (ShowLabel) {
        var row = "";
        row = '<thead> <tr> <th>AccountId </th><th>Error Description</th</tr></thead>';
        //row = row.slice(0, -1);
        //append Label row with line break
        //CSV += row + '\r\n';
    }
    CSV += row;
    CSV += '<tbody>';
    for (var i = 0; i < jsonResData.errors.length; i++) {
        row = '<tr><td>' + jsonResData.errors[i].rejectionAccountId + '</td> <td>' + jsonResData.errors[i].errorMessage + '</td></tr> ';
        CSV += row + '\r\n';
    }
    CSV += '</tbody>';
    if (CSV == '') {
        alert("Invalid data");
        return;
    }
}

function exportTableToCSV(filename) {
    var csv = [];
    var rows = document.querySelectorAll(".display tr");
    for (var i = 0; i < rows.length; i++) {
        var row = [],
            cols = rows[i].querySelectorAll("td,th");
        for (var j = 0; j < cols.length; j++)
            row.push(cols[j].innerText);
        csv.push(row.join(","));
    }
    // Download CSV file
    downloadCSV(csv.join("\n"), filename);
}

function downloadCSV(csv, filename) {
    var csvFile;
    var downloadLink;
    csvFile = new Blob([csv], {type: "text/csv"});// CSV file
    downloadLink = document.createElement("a");// Download link
    downloadLink.download = filename;// File name
    downloadLink.href = window.URL.createObjectURL(csvFile);// Create a link to the file
    downloadLink.style.display = "none";// Hide download link
    document.body.appendChild(downloadLink);// Add the link to DOM
    downloadLink.click();// Click download link
}

//Api Call for taking updated Migration
var getAcknowledge = document.getElementById("get-acknowledge");
getAcknowledge.addEventListener("click", function() {
    /*API CALL*/
    var ackReq = new XMLHttpRequest();
    var persistsAllDataRes;
    ackReq.open('GET', host+'dashboard/persistsAllData', false);
    ackReq.onload = function() {
        if (ackReq.status >= 200 && ackReq.status < 400) {
            persistsAllDataRes = ackReq.responseText;
            refreshData();
        } else {
            console.log("We connected to the server, but it returned an error.API-2");
        }
    };
    ackReq.onerror = function() {
        console.log("Connection error");
    };
    ackReq.send();
});

//----Refer from http://tobiasahlin.com/moving-letters/#
anime.timeline({
        loop: true
    })
    .add({
        targets: '.ml5 .line',
        opacity: [0.5, 1],
        scaleX: [0, 1],
        easing: "easeInOutExpo",
        duration: 700
    }).add({
        targets: '.ml5 .line',
        duration: 600,
        easing: "easeOutExpo",
        translateY: function(e, i, l) {
            var offset = -0.625 + 0.625 * 2 * i;
            return offset + "em";
        }
    }).add({
        targets: '.ml5 .ampersand',
        opacity: [0, 1],
        scaleY: [0.5, 1],
        easing: "easeOutExpo",
        duration: 600,
        offset: '-=600'
    }).add({
        targets: '.ml5 .letters-left',
        opacity: [0, 10],
        translateX: ["0.5em", 0],
        easing: "easeOutExpo",
        duration: 600,
        offset: '-=300'
    }).add({
        targets: '.ml5 .letters-right',
        opacity: [0, 1],
        translateX: ["-0.5em", 0],
        easing: "easeOutExpo",
        duration: 600,
        offset: '-=600'
    }).add({
        targets: '.ml5',
        opacity: 0,
        duration: 1000,
        easing: "easeOutExpo",
        delay: 1000
    });
//----




$(document).ready(function() {
	refreshData();
});




function refreshData()
{
	
    /* */
    /*API CALL - 1 Current Validation Stats*/
    var getMigrationValidationData = new XMLHttpRequest();
    getMigrationValidationData.open('GET', 'http://localhost:8080/dashboard/fetchMigrationValidationData', false);
    getMigrationValidationData.onload = function() {
        if (getMigrationValidationData.status >= 200 && getMigrationValidationData.status < 400) {
            currentValidationData = JSON.parse(getMigrationValidationData.responseText);
        } else {
            console.log("We connected to the server, but it returned an error.API-2");
        }
    };
    getMigrationValidationData.onerror = function() {
        console.log("Connection error");
    };
    getMigrationValidationData.send();

    /*API CALL - 2 Current Loaded Stats*/	
    var getLoadedData = new XMLHttpRequest();
    getLoadedData.open('GET',host+'dashboard/fetchMigrationLoadedData',false);
    getLoadedData.onload = function() {
    	if (getLoadedData.status >= 200 && getLoadedData.status < 400) {
    		currentLoadedData = JSON.parse(getLoadedData.responseText);
    	} else {
    		console.log("We connected to the server, but it returned an error.API-2");
    	}
    };
    getLoadedData.onerror = function() {
        console.log("Connection error");
    };
    getLoadedData.send();

    /*API CALL - 3  Currrent Migration Entities*/
    var getEntitiesRequest = new XMLHttpRequest();
    getEntitiesRequest.open('GET', host+'dashboard/fetchEntityData');
    getEntitiesRequest.onload = function() {
        if (getEntitiesRequest.status >= 200 && getEntitiesRequest.status < 400) {
            curMigEntityDetails = JSON.parse(getEntitiesRequest.responseText);
            var selectValidationElement = document.getElementById('selectValidationEntities');
            var selectLoadedElement = document.getElementById('selectLoadedEntities');
            selectValidationElement.innerHTML = selectValidationElement.innerHTML + '<option value="">Select Entity</option>';
            selectLoadedElement.innerHTML = selectLoadedElement.innerHTML + '<option value="">Select Entity</option>';
            for (var i = 0; i < curMigEntityDetails.length; i++) {
                if (i == 0) {
                    selectValidationElement.innerHTML = selectValidationElement.innerHTML + '<option selected="selected" value="' + curMigEntityDetails[i]['entityType'] + '">' + curMigEntityDetails[i]['entityType'] + '</option>';
                    selectLoadedElement.innerHTML = selectLoadedElement.innerHTML + '<option selected="selected" value="' + curMigEntityDetails[i]['entityType'] + '">' + curMigEntityDetails[i]['entityType'] + '</option>';
                } else {
                    selectValidationElement.innerHTML = selectValidationElement.innerHTML + '<option value="' + curMigEntityDetails[i]['entityType'] + '">' + curMigEntityDetails[i]['entityType'] + '</option>';
                    selectLoadedElement.innerHTML = selectLoadedElement.innerHTML + '<option value="' + curMigEntityDetails[i]['entityType'] + '">' + curMigEntityDetails[i]['entityType'] + '</option>';
                }
            }
            showValidationMeter();
            showLoadedMeter();
        } else {
            console.log("We connected to the server, but it returned an error. API-1");
        }
    };
    getEntitiesRequest.onerror = function() {
        console.log("Connection error");
    };
    getEntitiesRequest.send();
    $('#selectValidationEntities').empty();
    $('#selectLoadedEntities').empty();



    /*API CALL - 4 Fetch progression chart details*/
    var fetchProgressChartRequest = new XMLHttpRequest();
    fetchProgressChartRequest.open('GET', host+'dashboard/fetchProgressChart', false);
    fetchProgressChartRequest.onload = function() {
        if (fetchProgressChartRequest.status >= 200 && fetchProgressChartRequest.status < 400) {
            progressChartDetails = JSON.parse(fetchProgressChartRequest.responseText);
            //console.log(fetchProgressChartRequest.responseText);
        } else {
            console.log("We connected to the server, but it returned an error.API-4");
        }
    };
    fetchProgressChartRequest.onerror = function() {
        console.log("Connection error");
    };
    fetchProgressChartRequest.send();

    /*API CALL - 5 All Entities from whole migration process*/
    /*var getEntitiesRequest = new XMLHttpRequest();
    getEntitiesRequest.open('GET','http://localhost:8080/dashboard/fetchEntityData');
    getEntitiesRequest.onload = function() {
    	if (getEntitiesRequest.status >= 200 && getEntitiesRequest.status < 400) {
    		allMigEntityDetails = JSON.parse(getEntitiesRequest.responseText);
    		var selectElement2 = document.getElementById('selectProgressionChartEntities');
    		selectElement2.innerHTML = selectElement2.innerHTML + '<option value="">Select Entity</option>';
    		for (var i = 0; i < allMigEntityDetails.length; i++) {
    			selectElement2.innerHTML = selectElement2.innerHTML + '<option value="' + allMigEntityDetails[i]['entityType'] + '">' + allMigEntityDetails[i]['entityType'] + '</option>';
    		}
    		console.log("selectElement",selectElement2);
    	} else {
    		console.log("We connected to the server, but it returned an error.API-3");
    	}
    };
    getEntitiesRequest.onerror = function() {
        console.log("Connection error");
    };
    getEntitiesRequest.send();
    $('#selectProgressionChartEntities').empty();*/
    $.ajax({
        type: 'GET',
        url: host+'dashboard/fetchEntityData',
        async: false,
        success: function(allMigEntityDetails) {
            var selectElement2 = document.getElementById('selectProgressionChartEntities');
            selectElement2.innerHTML = selectElement2.innerHTML + '<option value="">Select Entity</option>';
            for (var i = 0; i < allMigEntityDetails.length; i++) {
                if (i == 0) {
                    selectElement2.innerHTML = selectElement2.innerHTML + '<option selected="selected" value="' + allMigEntityDetails[i]['entityType'] + '">' + allMigEntityDetails[i]['entityType'] + '</option>';
                } else {
                    selectElement2.innerHTML = selectElement2.innerHTML + '<option value="' + allMigEntityDetails[i]['entityType'] + '">' + allMigEntityDetails[i]['entityType'] + '</option>';
                }
            }
            showProgressionChart();
        }
    });
	
}