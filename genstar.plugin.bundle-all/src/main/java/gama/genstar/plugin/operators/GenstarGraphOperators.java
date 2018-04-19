package main.java.gama.genstar.plugin.operators;

import core.metamodel.entity.ADemoEntity;
import main.java.gama.genstar.plugin.type.GamaPopGenerator;
import main.java.gama.genstar.plugin.utils.GenStarGamaUtils;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.precompiler.GamlAnnotations.operator;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.graph.IGraph;
import msi.gaml.species.GamlSpecies;
import spin.SpinNetwork;
import spin.algo.factory.SpinNetworkFactory;

public class GenstarGraphOperators {
	@operator(value = "add_network", can_be_const = true, category = { "Gen*" }, concept = { "Gen*"})
	public static GamaPopGenerator addGraphGenerator(IScope scope, GamaPopGenerator gen, String graphName, String graphGenerator) throws GamaRuntimeException {
		gen.addNetworkGenerator(graphName, SpinNetworkFactory.getInstance().getSpinPopulationGenerator(graphName, graphGenerator));
		return gen;
	}

	@operator(value = "get_network", can_be_const = true, category = { "Gen*" }, concept = { "Gen*"})
	public static IGraph get_graph(IScope scope, GamaPopGenerator gen, String networkName) {
		SpinNetwork net = gen.getNetwork(networkName);		
		return GenStarGamaUtils.toGAMAGraph(scope, net, gen);
	}
	
	@operator(value = "associate_population_agents", can_be_const = true, category = { "Gen*" }, concept = { "Gen*"})	
	public static GamaPopGenerator associatePopulation(IScope scope, GamaPopGenerator gen, GamlSpecies pop) {
	//	IContainer<?, ? extends IAgent> c = pop.getAgents(scope);
		Object[] entity = gen.getGeneratedPopulation().toArray();
		
		for(int i = 0 ; i < pop.getPopulation(scope).length(scope) ; i ++) {			
			IAgent agt = pop.getPopulation(scope).getAgent(i);
			gen.addAgent( (ADemoEntity) entity[i], agt);
		}
		return gen;
	}	
}