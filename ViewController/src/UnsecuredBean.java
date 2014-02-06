import oracle.adf.view.rich.component.rich.output.RichOutputText;

public class UnsecuredBean {
    private RichOutputText ot2;

    public UnsecuredBean() {
    }

    public void setOt2(RichOutputText ot2) {
        this.ot2 = ot2;
    }

    public RichOutputText getOt2() {
        return ot2;
    }
    
    public void printSomething(){
        System.out.println("************** UnsecuredTF executed ");
    }
}
