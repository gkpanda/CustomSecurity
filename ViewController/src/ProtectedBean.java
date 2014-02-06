import oracle.adf.view.rich.component.rich.output.RichOutputText;

public class ProtectedBean {
    private RichOutputText ot1;

    public ProtectedBean() {
    }

    public void setOt1(RichOutputText ot1) {
        this.ot1 = ot1;
    }

    public RichOutputText getOt1() {
        return ot1;
    }
    
    public void printSomething(){
        System.out.println("******* Inside protected TF");
    }
}
