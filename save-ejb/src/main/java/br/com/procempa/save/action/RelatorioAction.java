package br.com.procempa.save.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import br.com.procempa.interceptors.ActionInterceptor;

/**
 * @author bogoni / rafael
 * 
 */
@Name("relatorioAction")
@Scope(ScopeType.CONVERSATION)
@ActionInterceptor
public class RelatorioAction extends AbstractSaveAction {
	private static final long serialVersionUID = 1L;
	
	
	@Observer("relatorioObserver")
	public void load(){
			
	}

	public void drawChartAssistencial(OutputStream out, Object data) throws IOException {
		load();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int i = 20; i<=24; i++)
		{
			dataset.setValue(new Double(i*3), "Solicitações criadas", "categoria"+i);
			dataset.setValue(new Double(i*2), "Solicitações resolvidas", "categoria"+i);
			dataset.setValue(new Double(i), "Solicitações abertas", "categoria"+i);			
		}

		JFreeChart chart = ChartFactory.createBarChart("Solicitações por bairros", "Solitações", "valor" , dataset, 
				PlotOrientation.VERTICAL,true, true, false);
		
		chart.getTitle().setFont(new Font("SansSerif", Font.PLAIN, 22));
		chart.getTitle().setPaint(Color.GRAY);
		chart.setBackgroundPaint(Color.WHITE);
	    chart.setBorderVisible(false);
	    
		
		CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);		

		// set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
	
		BufferedImage bufferedImage = chart.createBufferedImage(350, 300);
		ImageIO.write(bufferedImage, "gif", out);
	}
	   /*
	@Out(required = true)
	@In(required = false, create = true)
	private CausaAgenda causaAgenda;

	@In(value = CausaAgendaService.SERVICE_NAME, create = true)
	private ICausaAgendaService causaAgendaService;
	
	@DataModel
	private List<CausaAgenda> causaAgendaLista;

	@Factory("causaAgendaLista")
	@Observer("causaAgendaObserver")
	public void loadCausaAgendaLista() {
		this.setCausaAgendaLista(causaAgendaService.findAll());			
	}
		
	public String filtrar(){
		Events.instance().raiseEvent("causaAgendaObserver");
		return "listar";
	}
		
	
	public String editar(CausaAgenda causaAgenda) {
		setCausaAgenda(causaAgenda);
		return super.editar();
	}
	
	public String salvar() {
		Long id = causaAgenda.getId();
		if(BooleanUtils.isTrue(causaAgendaService.salvar(causaAgenda))){
			causaAgenda = causaAgendaService.refresh(causaAgendaService.evict(causaAgenda));
			Events.instance().raiseEvent("causaAgendaObserver");
			if(null == id){
				criar();
			}			
		}
		return "listar";
	}

	public String criar() {
		this.causaAgenda = new CausaAgenda();		
		return "criar";
	}
 
	public String excluir(CausaAgenda causaAgenda) {
		setCausaAgenda(causaAgenda);
		causaAgendaService.excluir( getCausaAgenda() );
		Events.instance().raiseEvent("causaAgendaObserver");
		return "listar";
	}
	
    public String cancelar() {  
        if (null != causaAgenda.getId()) {  
             causaAgendaService.refresh(causaAgenda);    
        }
	   Events.instance().raiseEvent("causaAgendaObserver");        
       return "listar";  
   }  

	public CausaAgenda getCausaAgenda() {
		return causaAgenda;
	}

	public void setCausaAgenda(CausaAgenda causaAgenda) {
		this.causaAgenda = causaAgenda;
	}

	public List<CausaAgenda> getCausaAgendaLista() {
		return causaAgendaLista;
	}

	public void setCausaAgendaLista(List<CausaAgenda> causaAgendaLista) {
		this.causaAgendaLista = causaAgendaLista;
	}
	*/
}

