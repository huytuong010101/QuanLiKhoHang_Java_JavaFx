package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;

public class Controller implements Initializable {
	@FXML
	public TableView<Data> tableView;
	public TableColumn<Data, String> idColumn;
	public TableColumn<Data, String> nameColumn;
	public TableColumn<Data, Integer> numColumn;
	public TableColumn<Data, Integer> priceColumn;
	public TableColumn<Data, String> dateColumn;
	public TableColumn<Data, String> typeColumn;
	public TableColumn<Data, String> noteColumn;
	public ObservableList<Data> listData;
	public ObservableList<String> listType;
	public TextField txtId;
	public TextField txtName;
	public TextField txtNum;
	public TextField txtPrice;
	public TextField txtDate;
	public ComboBox<String> cbType;
	public TextArea txtNote;
	public Calendar calendar = Calendar.getInstance();
	public Alert alert;
	public TextField txtNameOut;
	public TextField txtNumOut;
	public Text txtSumOut;
	public TextField txtPriceOut;
	public void addToTable(ActionEvent e) {
		if (cbType.getValue() == null) {
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lỗi dữ liệu nhập vào");
			alert.setContentText("Vui lòng chọn loại sản phẩm");
			alert.show();
			return;
		}
		if (!checkId(txtId.getText())) {
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lỗi dữ liệu nhập vào");
			alert.setContentText("ID đã trùng với 1 sản phẩm khác");
			alert.show();
			return;
		}
		try {
			int a = Integer.parseInt(txtNum.getText());
			int b = Integer.parseInt(txtPrice.getText());
			if ((a <= 0) || (b <= 0)){
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Lỗi dữ liệu nhập vào");
				alert.setContentText("Số lượng và giá tiền bắt bộc phải là số nguyên dương");
				alert.show();
				return;
			} 
			if (b % 1000 != 0) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Lỗi dữ liệu nhập vào");
				alert.setContentText("Giá tiền phải là số chia hết cho 1000");
				alert.show();
				return;
			}
			
		} catch (Exception e2) {
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lỗi dữ liệu nhập vào");
			alert.setContentText("Số lượng và giá tiền bắt bộc phải là số nguyên dương");
			alert.show();
			return;
		}
		String dateString = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
		listData.add(new Data(txtId.getText(), txtName.getText(), Integer.parseInt(txtNum.getText()), Integer.parseInt(txtPrice.getText()), dateString, cbType.getValue(), txtNote.getText()));
		
	}
	public void clickTable(MouseEvent e) {
		txtNameOut.setText(tableView.getSelectionModel().getSelectedItem().getName());
		txtPriceOut.setText(String.valueOf(((tableView.getSelectionModel().getSelectedItem().getPrice()))));
		txtNumOut.setText(String.valueOf(((tableView.getSelectionModel().getSelectedItem().getNum()))));
		
		
	}
	public void reset(ActionEvent e) {
		txtId.setText("");
		txtName.setText("");
		txtNote.setText("");
		txtPrice.setText("");
		txtNum.setText("");
	}
	public void checkOut(ActionEvent e) {
		try {
			String name = txtNameOut.getText();
			int num = Integer.parseInt(txtNumOut.getText());
			for (int i = 0;i < listData.size();i++) {
				Data tmpData = listData.get(i);
				if (tmpData.getName().equals(name)) {
					if (tmpData.getNum() < num) {
						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Thông báo");
						alert.setHeaderText("Lỗi dữ liệu nhập vào");
						alert.setContentText("Vui lòng kiểm tra lại các trường dử liệu");
						alert.show();
						
					} else {
						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Xác nhận");
						alert.setHeaderText("Xác nhận xuất kho");
						alert.setContentText("Bạn có chắc muốn xuất " + num + " " + name);
						Optional<ButtonType> btnClickOptional = alert.showAndWait();
						if (btnClickOptional.get() == ButtonType.OK) {
							if (num == tmpData.getNum()) {
								listData.remove(tmpData);
							} else {
								listData.set(i, new Data(tmpData.getId(),tmpData.getName(),tmpData.getNum()-num,tmpData.getPrice(),tmpData.getDate(),tmpData.getType(),tmpData.getNote()));
							}
						}
						
					}
					
					return; 
				}
			}
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lỗi dữ liệu nhập vào");
			alert.setContentText("Tên sản phẩm không tồn tại");
			alert.show();
		
			
		} catch (Exception e2) {
			// TODO: handle exception
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Lỗi dữ liệu nhập vào");
			alert.setContentText("Vui lòng kiểm tra lại các trường dử liệu");
			alert.show();
			return;
		}
	}
	public boolean checkId(String id) {
		for (Data data : listData) {
			if (data.getId().equals(id)) return false;
		}
		return true;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// du lieu tableView
		listData = FXCollections.observableArrayList(
				new Data("01", "Keo mut", 10, 5000, "01/01/2020", "Keo", ""),
				new Data("02", "Bao gao", 15, 3000, "02/01/2020", "Banh", "Ngon")
		);
		tableView.setItems(listData);
		idColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("name"));
		numColumn.setCellValueFactory(new PropertyValueFactory<Data, Integer>("num"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Data, Integer>("price"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("type"));
		noteColumn.setCellValueFactory(new PropertyValueFactory<Data, String>("note"));
		//Du lieu comboBoxType
		listType = FXCollections.observableArrayList();
		listType.addAll("Bánh","Kẹo","Trái cây");
	
		cbType.setItems(listType);
		//hien thi sum
		txtNumOut.textProperty().addListener((o,a,b) -> {
			System.out.println(o.getValue() + " - " + a + "-" + b);
			System.out.println(txtPriceOut.getText());

			
			try {
				
					int price = Integer.parseInt(o.getValue()) * Integer.parseInt(txtPriceOut.getText());
					txtSumOut.setText(String.valueOf(price));
				
			} catch (Exception e) {
				// TODO: handle exception
				txtSumOut.setText("0");
			}
			
		});
		//Load du lieu
		
	}
	
	
}