export interface NavItem {
  displayName: string;
  iconName: string;
  route?: string;
  command?(): void;
  children?: NavItem[];
}
