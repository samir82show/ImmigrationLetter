package entity.domain;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import entity.domain.util.JsfUtil;
import entity.domain.util.PaginationHelper;
import facade.EmployeeFacade;
import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

@Named("employeeController")
@SessionScoped
public class EmployeeController implements Serializable {

    private String formType;
    private Employee current;
    private DataModel items = null;
    @EJB
    private facade.EmployeeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EmployeeController() {
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public Employee getSelected() {
        if (current == null) {
            current = new Employee();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EmployeeFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Employee) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Employee();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmployeeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Employee) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmployeeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Employee) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmployeeDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Employee getEmployee(java.lang.String id) {
        return ejbFacade.find(id);
    }

    public void generateForm() throws IOException, ArabicShapingException, ParseException {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        if (formType.equals("1")) {
            DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dateParser = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
            PDDocument doc = PDDocument.load(new File("W:\\Common\\immigration_test\\EmployeeTransfer.pdf"));
            PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
            PDResources formResources = acroForm.getDefaultResources();
            PDType0Font font = PDType0Font.load(doc, new File("W:\\Common\\immigration_test\\arial.ttf"));
            formResources.put(COSName.getPDFName("F0"), font);
            ((PDTextField) acroForm.getField("15")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("15").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE_TASHKEEL_ISOLATED).shape(current.getName())).reverse().toString());
            ((PDTextField) acroForm.getField("14")).setDefaultAppearance("/Helv 0 Tf 0 g");
            acroForm.getField("14").setValue(String.valueOf(current.getQid().charAt(0)));
            acroForm.getField("13").setValue(String.valueOf(current.getQid().charAt(1)));
            acroForm.getField("12").setValue(String.valueOf(current.getQid().charAt(2)));
            acroForm.getField("11").setValue(String.valueOf(current.getQid().charAt(3)));
            acroForm.getField("10").setValue(String.valueOf(current.getQid().charAt(4)));
            acroForm.getField("9").setValue(String.valueOf(current.getQid().charAt(5)));
            acroForm.getField("8").setValue(String.valueOf(current.getQid().charAt(6)));
            acroForm.getField("7").setValue(String.valueOf(current.getQid().charAt(7)));
            acroForm.getField("6").setValue(String.valueOf(current.getQid().charAt(8)));
            acroForm.getField("5").setValue(String.valueOf(current.getQid().charAt(9)));
            acroForm.getField("4").setValue(String.valueOf(current.getQid().charAt(10)));
            ((PDTextField) acroForm.getField("25")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("25").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE).shape(current.getMaritalStatus())).reverse().toString());
            ((PDTextField) acroForm.getField("17")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("17").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE).shape(current.getOccupation())).reverse().toString());
            ((PDTextField) acroForm.getField("16")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("16").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE).shape(current.getNationality())).reverse().toString());
            acroForm.getField("22").setValue(dateFormatter.format(dateParser.parse(current.getIDExpiry().toString())));
            acroForm.getField("24").setValue(dateFormatter.format(dateParser.parse((current.getDOB().toString()))));
            System.out.println("dateFormatter.format(current.getIDExpiry())......................" + dateFormatter.format(dateParser.parse(current.getIDExpiry().toString())));
            doc.save("W:\\Common\\immigration_test\\EmployeeTransfer" + current.getQid() + ".pdf");
            doc.close();
        } else if (formType.equals("2")) {
            DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dateParser = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
            PDDocument doc = PDDocument.load(new File("W:\\Common\\immigration_test\\RPLicenseRenewalRequest.pdf"));
            PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
            PDResources formResources = acroForm.getDefaultResources();
            PDType0Font font = PDType0Font.load(doc, new File("W:\\Common\\immigration_test\\arial.ttf"));
            formResources.put(COSName.getPDFName("F0"), font);
            ((PDTextField) acroForm.getField("15")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("4").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE_TASHKEEL_ISOLATED).shape(current.getName())).reverse().toString());
            acroForm.getField("5").setValue(String.valueOf(current.getQid().charAt(0)));
            acroForm.getField("6").setValue(String.valueOf(current.getQid().charAt(1)));
            acroForm.getField("7").setValue(String.valueOf(current.getQid().charAt(2)));
            acroForm.getField("8").setValue(String.valueOf(current.getQid().charAt(3)));
            acroForm.getField("9").setValue(String.valueOf(current.getQid().charAt(4)));
            acroForm.getField("10").setValue(String.valueOf(current.getQid().charAt(5)));
            acroForm.getField("11").setValue(String.valueOf(current.getQid().charAt(6)));
            acroForm.getField("12").setValue(String.valueOf(current.getQid().charAt(7)));
            acroForm.getField("13").setValue(String.valueOf(current.getQid().charAt(8)));
            acroForm.getField("14").setValue(String.valueOf(current.getQid().charAt(9)));
            acroForm.getField("15").setValue(String.valueOf(current.getQid().charAt(10)));
            ((PDTextField) acroForm.getField("25")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("25").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE).shape(current.getMaritalStatus())).reverse().toString());
            ((PDTextField) acroForm.getField("17")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("17").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE).shape(current.getOccupation())).reverse().toString());
            ((PDTextField) acroForm.getField("16")).setDefaultAppearance("/F0 0 Tf 0 g");
            acroForm.getField("16").setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE).shape(current.getNationality())).reverse().toString());
            acroForm.getField("22").setValue(dateFormatter.format(dateParser.parse(current.getIDExpiry().toString())));
            acroForm.getField("24").setValue(dateFormatter.format(dateParser.parse((current.getDOB().toString()))));
            System.out.println("dateFormatter.format(current.getIDExpiry())......................" + dateFormatter.format(dateParser.parse(current.getIDExpiry().toString())));
            doc.save("W:\\Common\\immigration_test\\RPLicenseRenewalRequest" + current.getQid() + ".pdf");
            doc.close();

        }
    }

    @FacesConverter(forClass = Employee.class)
    public static class EmployeeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeController controller = (EmployeeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeController");
            return controller.getEmployee(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Employee) {
                Employee o = (Employee) object;
                return getStringKey(o.getQid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Employee.class.getName());
            }
        }

    }

}
