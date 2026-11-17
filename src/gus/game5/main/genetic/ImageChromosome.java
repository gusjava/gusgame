package gus.game5.main.genetic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilRandom;

public class ImageChromosome implements Chromosome<ImageChromosome> {

	public static final int GENE_COUNT = 60;

	private final int width;
	private final int height;
	private final List<ShapeGene> genes;

	public ImageChromosome(int width, int height) {
		this(width, height, randomGenes(width, height));
	}

	private ImageChromosome(int width, int height, List<ShapeGene> genes) {
		this.width = width;
		this.height = height;
		this.genes = genes;
	}

	private static List<ShapeGene> randomGenes(int width, int height) {
		List<ShapeGene> genes = new ArrayList<>();
		for(int i=0;i<GENE_COUNT;i++) genes.add(ShapeGene.random(width, height));
		return genes;
	}

	public void render(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);
		for(ShapeGene gene : genes) gene.render(g2);
	}

	public BufferedImage toImage() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		render(g2);
		g2.dispose();
		return img;
	}

	public ImageChromosome crossover(ImageChromosome other) {
		List<ShapeGene> childGenes = new ArrayList<>(genes.size());
		for(int i=0;i<genes.size();i++) {
			ShapeGene source = UtilRandom.randomBoolean() ? genes.get(i) : other.genes.get(i);
			childGenes.add(source.copy());
		}
		return new ImageChromosome(width, height, childGenes);
	}

	public void mutate(double mutationRate) {
		for(ShapeGene gene : genes) {
			if(UtilRandom.randomDouble(1.0)<mutationRate) gene.mutate(width, height);
		}
	}

	public ImageChromosome copy() {
		List<ShapeGene> copied = new ArrayList<>(genes.size());
		for(ShapeGene gene : genes) copied.add(gene.copy());
		return new ImageChromosome(width, height, copied);
	}

}
