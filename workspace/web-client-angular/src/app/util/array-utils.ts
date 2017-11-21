export class ArrayUtils {

  static groupBy<K, V>(keyFun: (v: V) => K): (items: V[]) => Map<K, V[]>;
  static groupBy<K, V>(items: V[], keyFun: (v: V) => K): Map<K, V[]>;
  static groupBy<K, V>(a, b?) {
    if (a instanceof Array) {
      return this.groupBy(b)(a);
    }
    if (a instanceof Function) {
      return (items: V[]) => {
        const map = new Map<K, V[]>();
        items.forEach(item => {
          let key = a(item);
          if (map.has(key)) {
            map.get(key).push(item)
          } else {
            map.set(key, [item]);
          }
        });
        return map;
      }
    }
  }
}
