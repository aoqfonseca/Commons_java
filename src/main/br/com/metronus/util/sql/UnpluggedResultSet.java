package br.com.neoris.util.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class UnpluggedResultSet {
        private Collection linhas = new ArrayList();
        private LinhaResultSet linha ;
        private Iterator iterator = null;        
                      
        
        public String getString(int index){
        	return (String)linha.get(index);
        }
        public int getInt(int index){
        	return linha.get(index)==null?0:((Integer)linha.get(index)).intValue();
        }        
        
        
        /**
         * Metodo para adicionar um campo 
         * @param index
         * @param element
         */
        protected final void addCampo(int index,String nomeCampo, Object element){
                linha.add(index,nomeCampo, element);
        }
        /**
         * Metodo para forçar a criação de um nova linha de dados
         *
         */
        protected final void novaLinha(){
                linhas.add(linha);
                linha = new LinhaResultSet();
        }
        /**
         * Metodo para verificar se existem linhas a serem lidas.
         * 
         * @return Retorna true caso existe e false caso nÃ£o existam mais
         */                       
        public final boolean next(){
                if(iterator == null){
                        iterator = linhas.iterator();
                }               
                if(iterator.hasNext()){
                        linha = (LinhaResultSet)iterator.next();
                        return true;
                }
                return false;
        }
        
        public final  void addCampo(int index,String nomeCampo, byte element) {	
        	linha.addPrimitiveElement(index,nomeCampo, element);
		}
		public final void addCampo(int index,String nomeCampo, boolean element) {
			linha.addPrimitiveElement(index,nomeCampo, element);
			
		}
		public final void addCampo(int index,String nomeCampo, long element) {
			linha.addPrimitiveElement(index,nomeCampo, element);
			
		}
		public final void addCampo(int index,String nomeCampo, double element) {
			linha.addPrimitiveElement(index,nomeCampo, element);			
		}
		
		public final void addCampo(int index,String nomeCampo, short element){
			linha.addPrimitiveElement(index,nomeCampo, element);
		}
		
		public final void addCampo(int index,String nomeCampo, char element){
			linha.addPrimitiveElement(index,nomeCampo, element);
		}		
		
		public final void addCampo(int index ,String nomeCampo, int element){
			linha.addPrimitiveElement(index,nomeCampo, element);
		}
        
        //Classe interna para representa um linha de dados
        class LinhaResultSet {                
                private ArrayList list = new ArrayList();
                private ArrayList indices = new ArrayList();
                private void add(int index,String nomeCampo, Object element){
                   list.add(index, element);
                   indices.add(index,nomeCampo);
                }
                private void addPrimitiveElement(int index,String nomeCampo,long element){
                	list.add(index, new Long(element));
                	 indices.add(index,nomeCampo);
                }
				private void addPrimitiveElement(int index,String nomeCampo,double element){
					list.add(index, new Double(element));
					 indices.add(index,nomeCampo);
				}
				private void addPrimitiveElement(int index,String nomeCampo,boolean element){
					list.add(index, new Boolean(element));
					 indices.add(index,nomeCampo);
				}
				private void addPrimitiveElement(int index,String nomeCampo,int element){
					list.add(index, new Integer(element));
					 indices.add(index,nomeCampo);
                }
				private void addPrimitiveElement(int index,String nomeCampo,short element){
					list.add(index, new Short(element));
					 indices.add(index,nomeCampo);
                }
				private void addPrimitiveElement(int index ,String nomeCampo,char element){
					list.add(index, new Character(element));
					 indices.add(index,nomeCampo);
                }
				private void addPrimitiveElement(int index ,String nomeCampo,byte element){
					list.add(index, new Byte(element));
					 indices.add(index,nomeCampo);
                }							
                private Object get(int i){
                	return list.get(i);
                }
                private Object get(String nome){
                	int i = indices.indexOf(nome);
                	return list.get(i);
                }
        }	
}

