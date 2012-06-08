package wjw.test.jmx.annotation;

import org.wstone.jmx.*;

@MBean
@Description("���ʼ�����")
public class VisitorCounter {

  @ManagedAttribute(writable = false, readable = true)
  @Description("���ʴ���")
  private int visits;

  @ManagedOperation(impact = Impact.WRITE)
  @Description("���÷��ʴ���")
  public void resetVisits() {
    this.visits = 0;
  }

  @ManagedOperation(impact = Impact.WRITE)
  @Description("�����������÷��ʴ���")
  public void resetVisits(@Description("����") int visits) {
    this.visits = visits;
  }

  @ManagedOperation(impact = Impact.READ_WRITE)
  @Description("�ۼӷ��ʴ���")
  public int incVisits() {
    return ++this.visits;
  }

  @ManagedOperation(impact = Impact.WRITE)
  @Description("���÷��ʴ���")
  public void setVisits(@Description("����") int visits) {
    this.visits = visits;
  }

  public int getVisits() {
    return visits;
  }

}