package graph.test;

import java.math.BigInteger;
import java.util.ArrayList;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;
import graph.algorithm.MaximumClique;
import graph.io.utils.EdgeFactory;
import graph.io.utils.NodeFactory;
import graph.io.utils.UndirectedGraphFactory;
import graph.util.Edge;
import graph.util.Node;

/**
 * Metoda słuzy do mierzenia czasu wykonania algorytmu wyszukiwania największej
 * kliki dla losowych grafów o ustalonej wielkości (ilość wierzchołków) i
 * gęstości (prawdopodobieństwo połączenia 2 dowolnych wierzchołków).
 * 
 * @author bbarczynski
 * 
 */
public class RandomTester {
	/**
	 * Wielkość grafu - licza wierzchołków
	 */
	private int numVertex;

	/**
	 * Gęstość grafu - prawodpodobieństawo połaczenia krawędzią dowolnej pary
	 * wierzchołków
	 */
	private double probability;

	/**
	 * Ilość powtórzeń wykonania algorytmu dla losowego grafu
	 */
	private int numIteration;

	/**
	 * Generator grafów
	 */
	private GraphGenerator<Node, Edge> generator;

	/**
	 * Czasy wykonania poszczególnych iteracji uruchomienia algorytmu
	 */
	private ArrayList<BigInteger> times;

	/**
	 * 
	 * @param numVertex
	 *            Wielkość grafu - ilość wierzchołków w grafie
	 * @param probability
	 *            Gęstość grafu - prawodpodobieństawo połączenia krawędzią
	 *            dowolnej pary wierzchołków
	 * @param numIteration
	 *            ilość powtórzeń uruchomienie algorytmu
	 */
	public RandomTester(int numVertex, double probability, int numIteration) {
		super();
		this.numVertex = numVertex;
		this.probability = probability;
		this.numIteration = numIteration;

		generator = new ErdosRenyiGenerator<Node, Edge>(new UndirectedGraphFactory(), new NodeFactory(),
				new EdgeFactory(), numVertex, probability);
		times = new ArrayList<BigInteger>(numIteration);

	}

	/**
	 * Metoda uruchamia testowanie algorytmu dla losowych grafow
	 * 
	 * @return sredni czas wykonania algorytmu (dla jedngo grafu)
	 */
	public BigInteger run() {
		times.clear();
		for (int i = 0; i < numIteration; ++i) {
			Graph<Node, Edge> graph = generator.create();
			MaximumClique<Node, Edge> algorithm = new MaximumClique<Node, Edge>(graph);
			algorithm.getCliques();
			// System.out.println("Duration(n=" + numVertex + ", p=" +
			// probability + ") [" + i + "]="
			// + algorithm.getAlgorithmDuration());
			times.add(algorithm.getAlgorithmDuration());
		}
		return getAverageTimeForGraph();
	}

	/**
	 * 
	 * @return sredni czas wykonania algorytmu (dla jedngo grafu)
	 */
	public BigInteger getAverageTimeForGraph() {
		BigInteger sum = BigInteger.ZERO;
		BigInteger everage = BigInteger.ZERO;
		for (BigInteger singleTime : times) {
			sum = sum.add(singleTime);
		}
		if (numIteration > 0)
			everage = sum.divide(BigInteger.valueOf(numIteration));
		return everage;
	}

}
